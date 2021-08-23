package ru.appline.test;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.appline.framework.data.Currency;
import ru.appline.framework.pages.HomePage;
import ru.appline.test.base.BaseTests;

import java.util.Arrays;
import java.util.Collection;

import static ru.appline.framework.data.Currency.RUB;
import static ru.appline.framework.data.Currency.USD;

@DisplayName("Вклады: параметризованный тест")
@RunWith(Parameterized.class)
public class ParameterizedRenTest extends BaseTests {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {USD, 100000, 6, 10000, true},
                {RUB, 600000, 12, 60000, false},
                {RUB, 1200000, 18, 100000, true},
        });
    }

    @Parameterized.Parameter()
    public Currency currency;

    @Parameterized.Parameter(1)
    public int amount;

    @Parameterized.Parameter(2)
    public int period;

    @Parameterized.Parameter(3)
    public int replenish;

    @Parameterized.Parameter(4)
    public boolean isSelected;

    @Test
    public void startTest() {
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