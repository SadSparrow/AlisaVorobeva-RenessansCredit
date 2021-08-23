package ru.appline.framework.managers;

import java.util.concurrent.TimeUnit;

import static ru.appline.framework.util.PropConst.IMPLICITLY_WAIT;
import static ru.appline.framework.util.PropConst.PAGE_LOAD_TIMEOUT;

public class InitManager {

    private InitManager() {
    }

    private static final TestPropManager props = TestPropManager.getTestPropManager();
    private static final DriverManager driverManager = DriverManager.getDriverManager();

    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    public static void quitFramework() {
        driverManager.quitDriver();
    }
}