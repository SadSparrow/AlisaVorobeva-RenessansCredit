package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.data.Currency;

import java.util.List;
import java.util.Optional;

import static ru.appline.framework.util.AttributeConst.CHECKED;
import static ru.appline.framework.util.AttributeConst.OUTER_TEXT;
import static ru.appline.framework.util.FormatString.getIntFromString;
import static ru.appline.framework.util.FormatString.getStringWithoutSpaceLowerCase;
import static ru.appline.framework.util.HelperFormatter.*;

public class ContributionsPage extends BasePage {

    @FindBy(xpath = "//div[@class='calculator__currency-content']/label")
    private List<WebElement> currencyCheckbox;

    @FindBy(xpath = "//input[@name='amount']")
    private WebElement amountContributionInput;

    @FindBy(xpath = "//input[@name='replenish']")
    private WebElement replenishInput;

    @FindBy(xpath = "//select")
    private WebElement select;

    @FindBy(xpath = "//div[@class='calculator__content']//label[@class='calculator__check-block']")
    private List<WebElement> checkBoxes;

    @FindBy(xpath = "//span[@class='js-calc-amount']")
    private WebElement calcAmount;

    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    private WebElement calcReplenish;

    @FindBy(xpath = "//td/span[@class='js-calc-period']")
    private WebElement calcPeriod;

    @FindBy(xpath = "//span[@class='js-calc-earned']")
    private WebElement calcEarned;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    private WebElement calcResult;

    @FindBy(xpath = "//div[@class='jq-selectbox__select-text']")
    private WebElement selectVisibleText;

    //выбор валюты
    @Step("Выбрать вкладку 'Валюта: {currencyName}'")
    public ContributionsPage selectCurrencyTab(Currency currencyName) {
        for (WebElement currency : currencyCheckbox) {
            if (currency.getText().equals(currencyName.getTitle())) {
                if (!isSelectedCurrency(currency)) {
                    waitUtilElementToBeClickable(currency).click();
                }
                return this;
            }
        }
        Assert.fail("Вкладка 'валюта: " + currencyName + "' не найдена");
        return this;
    }

    //заполнить поле "сумма вклада" или "Ежемесячное пополнение"
    @Step("Заполнить поле '{fieldName}' значением '{value}'")
    public ContributionsPage fillFieldNumbers(String fieldName, int value) {
        switch (fieldName) {
            case "Сумма вклада" -> {
                fillNumbers(amountContributionInput, value);
                wait.until(attributeEqualsNumber(calcAmount, OUTER_TEXT, value));
            }
            case "Ежемесячное пополнение" -> {
                fillNumbers(replenishInput, value);
                checkReplenishSumWithRetry(5);
            }
            default -> Assert.fail("Поле '" + fieldName + "' отсутствует на странице");
        }
        return this;
    }

    @Step("Кликнуть checkbox '{checkboxName}' (перевести в положение: '{isSelected}')")
    public ContributionsPage checkBoxSelect(String checkboxName, boolean isSelected) {
        for (WebElement element : checkBoxes) {
            if (getStringWithoutSpaceLowerCase(element.getText()).contains(getStringWithoutSpaceLowerCase(checkboxName))) {
                if (isSelected(element) != isSelected) {
                    String marker = getAttributeOuterText(calcEarned);
                    waitUtilElementToBeClickable(element).click();
                    wait.until(ExpectedConditions.attributeToBe(element.findElement(By.xpath(".//input")), CHECKED, "true"));
                    wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, OUTER_TEXT, marker)));
                }
                return this;
            }
        }
        Assert.fail("CheckBox '" + checkboxName + "' отсутствует на странице");
        return this;
    }

    //проверка, что сумма вклада изменилась в поле "Есть сейчас"
    @Step("Проверить, что сумма вклада в поле 'Есть сейчас' равна '{expectedValue}'")
    public ContributionsPage checkAmount(int expectedValue) {
        Assert.assertEquals("Значение некорректно", expectedValue, getIntFromOuterText(calcAmount));
        return this;
    }

    //перегруженный метод выбора периода
    @Step("Выбрать период '{period}'")
    public ContributionsPage selectPeriod(String period) {
        Select selectPeriod = new Select(select);
        selectPeriod.selectByVisibleText(period);
        select(period);
        return this;
    }

    @Step("Выбрать период '{period}' месяца(-ев)")
    public ContributionsPage selectPeriod(int period) {
        Select selectPeriod = new Select(select);
        selectPeriod.selectByValue(String.valueOf(period));
        select(period + "");
        return this;
    }

    @Step("Проверить расчеты по вкладу")
    public ContributionsPage checkResultSum() {
        Assert.assertEquals("Сумма рассчитана некорректно",
                roundTwoDigits(getIntFromOuterText(calcAmount) + getDoubleFromOuterText(calcEarned) + getIntFromOuterText(calcReplenish)),
                getDoubleFromOuterText(calcResult), 0.0001);
        return this;
    }

    //Проверка открытия страницы, путём проверки Заголовка страницы
    @Step("Проверить заголовок страницы: '{title}'")
    public ContributionsPage checkOpenContributionsPage(String title) {
        return checkOpenPageByTitle(title);
    }

    private void fillNumbers(WebElement field, int value) {
        String valueMarker = getAttributeValue(field);
        if (getIntFromString(valueMarker) == value) {
            return;
        }
        fillInputField(field, String.valueOf(value));
        waitUntil(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(field, valueMarker)), "Поле не было заполнено");
        Assert.assertEquals("Поле было заполнено некорректно", getIntFromValue(field), value);
    }

    private boolean isSelectedCurrency(WebElement currency) {
        return getBooleanFromChecked(currency);
    }

    private void select(String period) {
        waitUntil(ExpectedConditions.attributeContains(selectVisibleText, OUTER_TEXT, period),
                "Ожидаемый период: " + period + ", но фактически был: " + getAttributeOuterText(selectVisibleText));
    }

    private boolean isSelected(WebElement checkBox) {
        return getBooleanFromChecked(checkBox.findElement(By.xpath(".//input")));
    }

    private void checkReplenishSumWithRetry(int countRetry) {
        int i = 0;
        boolean marker = true;

        while (i < countRetry && marker) {
            try {
                i++;
                wait = new WebDriverWait(driverManager.getDriver(), 1, 1000);
                wait.until(attributeEqualsNumber(calcReplenish, OUTER_TEXT, getReplenishSum()));
                marker = false;
            } catch (TimeoutException ignored) {
            } finally {
                wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);
            }
        }
        if (i == countRetry) {
            Assert.fail("Ожидаемое значение в поле 'Пополнение за " + getAttributeOuterText(calcPeriod) + "': "
                    + getReplenishSum() + ", фактическое значение: " + getAttributeOuterText(calcReplenish));
        }
    }

    private int getReplenishSum() {
        int period = getIntFromOuterText(calcPeriod);
        int replenish = getIntFromValue(replenishInput);
        return (period - 1) * replenish;
    }

    private static ExpectedCondition<Boolean> attributeEqualsNumber(WebElement element, String attribute, int value) {
        return driver -> Optional.of(element.getAttribute(attribute))
                .map(seen -> (getIntFromString(seen) == value))
                .orElse(false);
    }
}