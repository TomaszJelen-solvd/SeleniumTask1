package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;
    @FindBy(css = "input[id='user-name']")
    WebElement login;

    @FindBy(css = "input[id='password']")
    WebElement password;

    @FindBy(css = "input[id='login-button']")
    WebElement submit;

    @FindBy(css = "div[class='error-message-container error']")
    WebElement error;

    @FindBy(css = "h3[data-test='error']")
    WebElement errorText;

    public LoginPage(WebDriver driver) {
        driver.get("https://www.saucedemo.com/");
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillLogin(String fill) {
        login.sendKeys(fill);
    }

    public void fillPassword(String fill) {
        password.sendKeys(fill);
    }

    public InventoryPage clickSubmit() {
        submit.click();
        return new InventoryPage(driver);
    }

    public boolean checkErrorDisplayed() {
        return error.isDisplayed();
    }

    public boolean checkRightErrorDisplayed() {
        return errorText.getText().equals("Epic sadface: Username is required");
    }
}
