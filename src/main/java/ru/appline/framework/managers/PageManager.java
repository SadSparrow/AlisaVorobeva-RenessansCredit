package ru.appline.framework.managers;

import ru.appline.framework.pages.BasePage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PageManager {

    private static PageManager pageManager;
    private final Map<String, BasePage> pageMap = new HashMap<>();

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        if (pageMap.isEmpty() || pageMap.get(pageClass.getName()) == null) {
            try {
                pageMap.put(pageClass.getName(), pageClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (T) pageMap.get(pageClass.getName());
    }

    public void clearPageMap() {
        pageMap.clear();
    }
}