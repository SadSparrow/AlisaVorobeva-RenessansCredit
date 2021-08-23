package ru.appline.test;

import io.qameta.allure.junit4.DisplayName;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.appline.framework.data.Currency;
import ru.appline.framework.pages.HomePage;
import ru.appline.test.base.BaseTests;

@DisplayName("Вклады: тест с параметрами")
@RunWith(JUnitParamsRunner.class)
public class WithParamRenTest extends BaseTests {

    @Test
    @FileParameters("src/test/resources/data.csv")
    public void startTest(Currency currency, int amount, int period, int replenish, boolean isSelected) {
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
}
