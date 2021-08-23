package ru.appline.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.managers.PageManager;

public class BasePage {

    protected final DriverManager driverManager = DriverManager.getDriverManager();
    protected PageManager pageManager = PageManager.getPageManager();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);


    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    //явное ожидание того, что элемент станет кликабельным
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            Assert.fail("Элемент не кликабелен");
        }
        return element;
    }

    //заполнение inputа
    protected void fillInputField(WebElement field, String value) {
        waitUtilElementToBeClickable(field).click();
        field.clear();
        field.sendKeys(value);
    }

    protected void waitUntil(ExpectedCondition<Boolean> condition, String message) {
        try {
            wait.until(condition);
        } catch (TimeoutException e) {
            Assert.fail(message);
        }
    }

    protected <T extends BasePage> T checkOpenPageByTitle(String title) {
        Assert.assertEquals("Заголовок не соответствует требуемому", title, driverManager.getDriver().getTitle());
        return (T) this;
    }
}