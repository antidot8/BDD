package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
  private SelenideElement heading = $(withText("Пополнение карты"));
  private SelenideElement amountInput = $("[data-test-id=amount] input");
  private SelenideElement cardFrom = $("[data-test-id=from] input");
  private SelenideElement transferButton = $("[data-test-id=action-transfer]");
  public SelenideElement wrongAmount = $("[data-test-id=error-notification]");

  public TransferPage() {
    heading.shouldBe(visible);
  }

  public DashboardPage transferFrom(int amount, DataHelper.CardInfo cardInfo) {
    $(amountInput).setValue(Integer.toString(amount));
    $(cardFrom).setValue(cardInfo.getNumber());
    $(transferButton).click();
    return new DashboardPage();
  }

  public void notificationError() {
    wrongAmount.shouldBe(Condition.visible)
            .shouldHave(Condition.text("Превышен лимит доступных средств"));
  }
}
