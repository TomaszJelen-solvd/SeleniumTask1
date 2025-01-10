package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    public void wrongLoginSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        Assert.assertTrue(startingLoginPage.checkErrorDisplayed(), "Failed to display error");
        Assert.assertTrue(startingLoginPage.checkRightErrorDisplayed(), "Wrong error message");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void correctLoginSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Wrong url");
        Assert.assertTrue(inventoryPage.checkTitleDisplayed(), "Wrong content title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void cartCheckSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        int index = 0;
        inventoryPage.clickAddButton(index);
        String title = inventoryPage.getProductTitle(index);
        CartPage cartPage = inventoryPage.clickCart();
//        Assert.assertTrue(cartPage.checkProduct(index), "Wrong products");
        Assert.assertTrue(checkProductTitle(cartPage.getProductTitles(), title), "Wrong product title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    private static boolean checkProductTitle(List<String> productTitles, String title) {
        for (String productTitle : productTitles) {
            if (productTitle.equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkProductTitles(List<String> productTitles, List<String> titles) {
        for (String title : titles) {
            if (!checkProductTitle(productTitles, title)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void cartMultipleCheckSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
//        driver.manage().deleteAllCookies();
        int index = 0;
        inventoryPage.clickAddButton(index);
        String title = inventoryPage.getProductTitle(index);
        CartPage cartPage = inventoryPage.clickCart();
//        Assert.assertTrue(cartPage.checkProduct(index), "Wrong products");
        Assert.assertTrue(checkProductTitle(cartPage.getProductTitles(), title), "Wrong product title");
        Assert.assertTrue(checkProductTitles(cartPage.getProductTitles(), List.of(title)), "Wrong product title");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void listSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLogin("standard_user");
        loginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = loginPage.clickSubmit();
        List<ProductSubPage> productSubPages = inventoryPage.getProductSubPages();
        productSubPages.forEach(productSubPage -> logger.info(productSubPage.name.getText() + " " + productSubPage.price.getText()));
        Assert.assertEquals(productSubPages.size(), 6, "Wrong number of products");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void experimentTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        int index = 1;
        logger.info(inventoryPage.getProductTitle(index));
        logger.info(inventoryPage.getProductPrice(index));
        Assert.assertEquals(inventoryPage.protoGetProductPrice(index), new BigDecimal("9.99"), "Wrong price");
        Assert.assertEquals(inventoryPage.altGetProductPrice(index), new BigDecimal("9.99"), "Wrong price");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void addButtonSeleniumTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        int index = 3;
        String text = inventoryPage.getAddButtonText(index);
        inventoryPage.clickAddButton(index);
        Assert.assertNotEquals(inventoryPage.getAddButtonText(index), text, "Error with switching buttons");

//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    @Test
    public void cartMultipleCheckTitlePriceTest() throws MalformedURLException, InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriver driver = driverPool.getDriver();

//        driver.get("https://www.saucedemo.com/");
        LoginPage startingLoginPage = new LoginPage(driver);

        startingLoginPage.fillLogin("standard_user");
        startingLoginPage.fillPassword("secret_sauce");
        InventoryPage inventoryPage = startingLoginPage.clickSubmit();
        List<String> titles = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();
        for (int index : List.of(0, 2, 5)) {
//        int index = 0;
            inventoryPage.clickAddButton(index);
            titles.add(inventoryPage.getProductTitle(index));
            prices.add(inventoryPage.altGetProductPrice(index));
        }
        CartPage cartPage = inventoryPage.clickCart();
        Assert.assertTrue(checkProductTitlesPrices(cartPage.getProductTitles(), titles, cartPage.altGetProductPrices(), prices), "Error: Wrong products or prices");
//        Assert.assertTrue(cartPage.checkProductTitlesPrices(titles, prices), "Error: Wrong products or prices");
//        driver.quit();

        driverPool.releaseDriver(driver);
    }

    public boolean checkProductTitlePrice(List<String> productTitles, String title, List<BigDecimal> productPrices, BigDecimal price) {
        for (String productTitle : productTitles) {
            if (productTitle.equals(title)) {
                return productPrices.get(productTitles.indexOf(productTitle)).compareTo(price) == 0;
            }
        }
        return false;
    }

    public boolean checkProductTitlesPrices(List<String> productTitles, List<String> titles, List<BigDecimal> productPrices, List<BigDecimal> prices) {
        for (int i = 0; i < titles.size(); i++) {
            if (!checkProductTitlePrice(productTitles, titles.get(i), productPrices, prices.get(i))) {
                return false;
            }
        }
        return true;
    }

    @AfterTest
    public void afterTest() {
//        driver.quit();

        driverPool.close();
    }
}
