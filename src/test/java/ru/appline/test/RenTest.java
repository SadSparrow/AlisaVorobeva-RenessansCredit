package ru.appline.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.appline.framework.data.Currency;
import ru.appline.framework.pages.HomePage;
import ru.appline.test.base.BaseTests;

@DisplayName("Ренессанс Кредит")
class RenTest extends BaseTests {

    @DisplayName("Вклады: параметризованный тест")
    @ParameterizedTest(name = "параметры {0}, {1}, {2}, {3}, {4}")
    @CsvFileSource(resources = "/data.csv")
    void startTest(Currency currency, int amount, int period, int replenish, boolean isSelected) {
        app.getPage(HomePage.class)
                .checkOpenHomePage("Банк «Ренессанс Кредит»")
                .selectSubMenu("Вклады")
                .checkOpenContributionsPage("Вклады")
                .selectCurrencyTab(currency)
                .fillFieldNumbers("Сумма вклада", amount)
                .selectPeriod(period)
                .fillFieldNumbers("Ежемесячное пополнение", replenish)
                .checkAmount(amount)
                .checkBoxSelect("капитализация", isSelected)
                .checkResultSum();
    }

    @DisplayName("Вклады: USD")
    @Test
    void startTestUsd() {
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