package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class AppTest {
    static final Logger logger = LoggerFactory.getLogger(AppTest.class);
    @Test
    public void oneMoreTest() {
        logger.info("This is a TestNG-Maven based test");
    }

    @BeforeTest
    public void beforeTest() throws IOException {
        App.startServer();
    }

    @AfterTest
    public void afterTest() {
        App.stopServer();
    }

    WebDriver driver;

    @FindBy(css = "input[name='productname']")
    WebElement elementInput;

    @FindBy(css = "input[type='submit']")
    WebElement button;

    @FindBy(css="li")
    List<WebElement> selects;

    @Test
    public void oneMoreSeleniumTest() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
        try {
            driver.get("http://localhost:8000/");
            logger.info(driver.getPageSource());
            elementInput.sendKeys("mezopotamia");
            button.click();
//            Thread.sleep(5000);
            logger.info(driver.getPageSource());
            for (WebElement select : selects) {
                logger.info(select.getText());
            }
//            logger.info(selects.toString());

        } finally {
            driver.quit();
        }
        logger.info("Goodbye world!");
    }


}
