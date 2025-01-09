package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PopTest {
    static final Logger logger = LoggerFactory.getLogger(PopTest.class);

    DriverPool driverPool;

//    WebDriver driver;
//    LoginPage startingLoginPage;

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driverPool = DriverPool.getDriverPool();
    }

//    @BeforeMethod
//    public void beforeMethod() {
//        driver.get("https://www.saucedemo.com/");
//        startingLoginPage = new LoginPage(driver);
//    }

    @Test
    public void wrongLoginSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        Assert.assertTrue(startingLoginPage.checkErrorDisplayed(), "Failed to display error");
        Assert.assertTrue(startingLoginPage.checkRightErrorDisplayed(), "Wrong error message");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void correctLoginSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Wrong url");
        Assert.assertTrue(inventoryPage.checkTitleDisplayed(), "Wrong content title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void cartCheckSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        int index = 0;
        inventoryPage.clickAddButton(index);
        String title = inventoryPage.getProductTitle(index);
        CartPage cartPage = inventoryPage.clickCart(driver);
        Assert.assertTrue(cartPage.checkProduct(index), "Wrong products");
        Assert.assertTrue(cartPage.checkProductTitle(title), "Wrong product title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void cartMultipleCheckSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        int index = 0;
        inventoryPage.clickAddButton(index);
        String title = inventoryPage.getProductTitle(index);
        CartPage cartPage = inventoryPage.clickCart(driver);
        Assert.assertTrue(cartPage.checkProduct(index), "Wrong products");
        Assert.assertTrue(cartPage.checkProductTitle(title), "Wrong product title");
        Assert.assertTrue(cartPage.checkProductTitles(List.of(title)), "Wrong product title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void listSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLogin("standard_user");
        loginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = loginPage.clickSubmit(driver);
        List<ProductSubPage> productSubPages = inventoryPage.getProductSubPages();
        productSubPages.stream().forEach(productSubPage -> logger.info(productSubPage.name.getText() + " " + productSubPage.price.getText()));
        Assert.assertEquals(productSubPages.size(), 6, "Wrong number of products");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void experimentTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        int index = 1;
        logger.info(inventoryPage.getProductTitle(index));
        logger.info(inventoryPage.getProductPrice(index));
        Assert.assertEquals(inventoryPage.protoGetProductPrice(index), new BigDecimal("9.99"), "Wrong price");
        Assert.assertEquals(inventoryPage.altGetProductPrice(index), new BigDecimal("9.99"), "Wrong price");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void addButtonSeleniumTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        int index = 3;
        String text = inventoryPage.getAddButtonText(index);
        inventoryPage.clickAddButton(index);
        Assert.assertNotEquals(inventoryPage.getAddButtonText(index), text, "Error with switching buttons");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void cartMultipleCheckTitlePriceTest() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit(driver);
        List<String> titles = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();
        for (int index : List.of(0, 2, 5)) {
//        int index = 0;
            inventoryPage.clickAddButton(index);
            titles.add(inventoryPage.getProductTitle(index));
            prices.add(inventoryPage.altGetProductPrice(index));
        }
        CartPage cartPage = inventoryPage.clickCart(driver);
        Assert.assertTrue(cartPage.checkProductTitlesPrices(titles, prices), "Error: Wrong products or prices");
//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @AfterTest
    public void afterTest() {
//        driver.quit();

        driverPool.close();
    }
}
