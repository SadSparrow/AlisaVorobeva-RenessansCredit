package ru.appline.framework.util;

import java.util.Locale;

public class FormatString {

    private FormatString() {
    }

    public static String getStringWithoutSpaceLowerCase(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    public static int getIntFromString(String string) {
        return Integer.parseInt(string.toLowerCase(Locale.ROOT).replaceAll("[^\\d]", ""));
    }

    public static double getDoubleFromString(String string) {
        return Double.parseDouble(string.toLowerCase(Locale.ROOT).replaceAll("[^\\d(,|.)]", "").replace(",", "."));
    }
}