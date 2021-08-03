package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

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
    int Amount1 = dashboardBefore.getCardBalance(getFirstCard()) + transferAmount;
    if (transferAmount > dashboardBefore.getCardBalance(getFirstCard())) {
      Amount1 = dashboardBefore.getCardBalance(getFirstCard());
    }
    int Amount2 = dashboardBefore.getCardBalance(getSecondCard()) - transferAmount;
    if (transferAmount > dashboardBefore.getCardBalance(getSecondCard())) {
      Amount2 = dashboardBefore.getCardBalance(getSecondCard());
    }
    val dashboardAfter = dashboardBefore
            .transferTo(getFirstCard())
            .transferFrom(transferAmount, getSecondCard());
    val balance1 = dashboardAfter.getCardBalance(getFirstCard());
    val balance2 = dashboardAfter.getCardBalance(getSecondCard());
    assertEquals(Amount1, balance1);
    assertEquals(Amount2, balance2);
  }

  @Test
  void shouldTransferMoneyFromSecondToFirstCards() {
    val transferAmount = 1000;
    val dashboardBefore = new LoginPage()
            .validLogin(getAuthInfo())
            .validVerify(getVerificationCodeFor(getAuthInfo()));
    int Amount1 = dashboardBefore.getCardBalance(getFirstCard()) - transferAmount;
    if (transferAmount > dashboardBefore.getCardBalance(getFirstCard())) {
      Amount1 = dashboardBefore.getCardBalance(getFirstCard());
    }
    int Amount2 = dashboardBefore.getCardBalance(getSecondCard()) + transferAmount;
    if (transferAmount > dashboardBefore.getCardBalance(getSecondCard())) {
      Amount2 = dashboardBefore.getCardBalance(getSecondCard());
    }
    val dashboardAfter = dashboardBefore
            .transferTo(getSecondCard())
            .transferFrom(transferAmount, getFirstCard());
    val balance1 = dashboardAfter.getCardBalance(getFirstCard());
    val balance2 = dashboardAfter.getCardBalance(getSecondCard());
    assertEquals(Amount1, balance1);
    assertEquals(Amount2, balance2);
  }

  @Test
  void shouldNotLoginWithOtherUser() {
    val loginPage = new LoginPage();
    loginPage.invalidLogin(getOtherAuthInfo());
  }
}