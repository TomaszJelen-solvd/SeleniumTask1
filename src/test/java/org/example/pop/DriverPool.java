package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DriverPool {
    private static DriverPool driverPool;
    private List<WebDriver> availableDrivers;

    private DriverPool() {
        availableDrivers = new ArrayList<>();
    }

    public static DriverPool getDriverPool() {
        if (driverPool == null) {
            driverPool = new DriverPool();
        }
        return driverPool;
    }

    public WebDriver getDriver() throws MalformedURLException, InterruptedException {
        synchronized (this) {
//            if (availableDrivers.isEmpty()) {
                ChromeOptions options = new ChromeOptions();
                WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                return driver;
//            } else {
//                WebDriver driver = availableDrivers.remove(0);
////                System.out.println(driver.manage().getCookies().size());
//                driver.manage().deleteAllCookies();
//                Thread.sleep(7000);
////                ((WebStorage) driver).getLocalStorage().clear();
////                System.out.println(driver.manage().getCookies().size());
//                return driver;
//            }
        }
    }

    public void releaseDriver(WebDriver driver) throws InterruptedException {
        synchronized (this) {
//            Thread.sleep(7000);
            availableDrivers.add(driver);
        }
    }

    public void close() {
        availableDrivers.stream()
                .forEach(WebDriver::quit);
    }
}
