package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@class='service__title-text']")
    private List<WebElement> listSubMenu;

    @Step("Выбрать подменю '{nameSubMenu}'")
    public ContributionsPage selectSubMenu(String nameSubMenu) {
        for (WebElement menuItem : listSubMenu) {
            if (menuItem.getText().equalsIgnoreCase(nameSubMenu)) {
                waitUtilElementToBeClickable(menuItem.findElement(By.xpath("./../a"))).click();
                return pageManager.getPage(ContributionsPage.class);
            }
        }
        Assertions.fail("Подменю '" + nameSubMenu + "' не было найдено на стартовой странице");
        return pageManager.getPage(ContributionsPage.class);
    }

    @Step("Проверить заголовок страницы: '{title}'")
    public HomePage checkOpenHomePage(String title) {
        return checkOpenPageByTitle(title);
    }
}