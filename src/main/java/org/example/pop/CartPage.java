package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class CartPage {
    WebDriver driver;
    @FindBy(xpath = "//div[text()='Sauce Labs Backpack']")
    WebElement sauceLabsBackpack;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    List<WebElement> productTitles;

    @FindBy(xpath = "//div[@class='inventory_item_price']")
    List<WebElement> productPrices;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    public boolean checkProduct(int index) {
//        switch (index) {
//            case 0:
//                return sauceLabsBackpack.isDisplayed();
//            default:
//                return false;
//        }
//    }

    public boolean checkProductTitle(String title) {
        for (WebElement productTitle : productTitles) {
            if (productTitle.getText().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkProductTitlePrice(String title, BigDecimal price) {
        for (WebElement productTitle : productTitles) {
            if (productTitle.getText().equals(title)) {
                String text = productPrices.get(productTitles.indexOf(productTitle)).getText();
                BigDecimal number = new BigDecimal(Pattern.compile("\\d+(\\.\\d+|)").matcher(text).results().findFirst().get().group());
                return number.compareTo(price) == 0;
            }
        }
        return false;
    }

    public boolean checkProductTitles(List<String> titles) {
        for (String title : titles) {
            if (!this.checkProductTitle(title)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkProductTitlesPrices(List<String> titles, List<BigDecimal> prices) {
        for (int i = 0; i < titles.size(); i++) {
            if (!this.checkProductTitlePrice(titles.get(i), prices.get(i))) {
                return false;
            }
        }
        return true;
    }

    public List<String> getProductTitles() {
        return productTitles.stream().map(WebElement::getText).toList();
    }
    public List<BigDecimal> altGetProductPrices() {
        return productPrices.stream().map(WebElement::getText)
                .map(text -> new BigDecimal(Pattern.compile("\\d+(\\.\\d+|)")
                        .matcher(text).results().findFirst().get().group()))
                .toList();
    }



}
