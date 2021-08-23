package ru.appline.framework.data;

public enum Currency {
    RUB("Рубли"),
    USD("Доллары США");

    private final String title;

    Currency(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Currency fromString(String text) {
        for (Currency currency : Currency.values()) {
            if (currency.title.equalsIgnoreCase(text)) {
                return currency;
            }
        }
        return null;
    }
}
