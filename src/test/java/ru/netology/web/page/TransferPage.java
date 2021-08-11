package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
  private final SelenideElement amountInput = $("[data-test-id=amount] input");
  private final SelenideElement cardFrom = $("[data-test-id=from] input");
  private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
  public SelenideElement wrongAmount = $("[data-test-id=error-notification]");

  public TransferPage() {
    SelenideElement heading = $(withText("Пополнение карты"));
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
