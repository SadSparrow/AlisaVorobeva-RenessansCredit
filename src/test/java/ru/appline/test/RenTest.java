package ru.appline.test;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.appline.framework.data.Currency;
import ru.appline.framework.pages.HomePage;
import ru.appline.test.base.BaseTests;

@DisplayName("Ренессанс Кредит")
public class RenTest extends BaseTests {

    @DisplayName("Вклады: USD")
    @Test
    public void startTestUsd() {
        app.getPage(HomePage.class)
                .checkOpenHomePage("Банк «Ренессанс Кредит»")
                .selectSubMenu("Вклады")
                .checkOpenContributionsPage("Вклады")
                .selectCurrencyTab(Currency.USD)
                .fillFieldNumbers("Сумма вклада", 500_000)
                .selectPeriod("12 месяцев")
                .fillFieldNumbers("Ежемесячное пополнение", 20_000)
                .checkAmount(500_000)
                .checkBoxSelect("капитализация", true)
                .checkResultSum();
    }
}