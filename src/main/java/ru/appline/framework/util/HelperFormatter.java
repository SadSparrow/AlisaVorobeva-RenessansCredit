package ru.appline.framework.util;

import org.openqa.selenium.WebElement;

import static ru.appline.framework.util.AttributeConst.*;
import static ru.appline.framework.util.FormatString.getDoubleFromString;
import static ru.appline.framework.util.FormatString.getIntFromString;

public class HelperFormatter {

    private HelperFormatter() {
    }

    public static String getAttributeValue(WebElement element) {
        return element.getAttribute(VALUE);
    }

    public static String getAttributeOuterText(WebElement element) {
        return element.getAttribute(OUTER_TEXT);
    }

    public static String getAttributeChecked(WebElement element) {
        return element.getAttribute(CHECKED);
    }

    public static int getIntFromValue(WebElement element) {
        return getIntFromString(getAttributeValue(element));
    }

    public static int getIntFromOuterText(WebElement element) {
        return getIntFromString(getAttributeOuterText(element));
    }

    public static double getDoubleFromOuterText(WebElement element) {
        return getDoubleFromString(getAttributeOuterText(element));
    }

    public static boolean getBooleanFromChecked(WebElement element) {
        return Boolean.parseBoolean(getAttributeChecked(element));
    }

    public static double roundTwoDigits(double d) {
        return Math.round(d * 100.00) / 100.00;
    }
}