package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private final SelenideElement buttonReload = $("[data-test-id=\"action-reload\"]");
  private static final ElementsCollection cards = $$(".list__item");
  private static final String balanceStart = ", баланс: ";
  private static final String balanceFinish = " р.";
  private static final String cardIdStart = "[data-test-id=\"";
  private static final String cardIdFinish = "\"]";

  public DashboardPage() {
    SelenideElement heading = $(withText("Ваши карты"));
    heading.shouldBe(visible);
  }

  public TransferPage transferTo(DataHelper.CardInfo cardInfo) {
    $(cardIdStart + cardInfo.getId() + cardIdFinish + " button").click();
    return new TransferPage();
  }

  public int getCardBalance(DataHelper.CardInfo cardInfo) {
    val id = cardInfo.getId();
    String textWithId = $(cardIdStart + id + cardIdFinish).getText(); //получаем строку с id карты и остальным в виде текста
    String text = cards.find(text(textWithId)).getText();
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
}