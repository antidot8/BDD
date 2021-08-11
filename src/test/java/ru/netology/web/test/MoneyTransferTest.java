package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.data.DataHelper.getSecondCard;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCards() {
        val transferAmount = 1000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        val amount1 = DataHelper.dicreaseBalance(dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        val amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getSecondCard()), dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getSecondCard())
                .transferFrom(transferAmount, getFirstCard());
        val balance1 = dashboardAfter.getCardBalance(getFirstCard());
        val balance2 = dashboardAfter.getCardBalance(getSecondCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCards() {
        val transferAmount = 1000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        val amount1 = DataHelper.dicreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        val balance1 = dashboardAfter.getCardBalance(getSecondCard());
        val balance2 = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }

    @Test
    void shouldNotLoginWithOtherUser() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo());
    }

    // Следующий тест будет падать, т.к. система перевода допускает перевод сверх лимита карты
    @Test
    void shouldTransferMoneyFromSecondToFirstCardsUnderLimit() {
        val transferAmount = 20000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        val amount1 = DataHelper.dicreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        val balance1 = dashboardAfter.getCardBalance(getSecondCard());
        val balance2 = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }
}