package org.example.pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InventoryPage {

    @FindBy(xpath = "//div[text()='Swag Labs']")
    WebElement title;
    @FindBy(css = "a[class='shopping_cart_link']")
    WebElement cart;


    @FindBy(css = "div[class='inventory_item']")
    List<WebElement> products;
    @FindBy(xpath = "//div[@class='inventory_item_name ']")
    List<WebElement> productTitles;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    List<WebElement> productPrices;
    @FindBy(xpath = "//div[@class='inventory_item_price']/following-sibling::button")
    List<WebElement> productAddButtons;


    public InventoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<ProductSubPage> getProductSubPages() {
        return products.stream().map(ProductSubPage::new).collect(Collectors.toList());
    }

    public boolean checkTitleDisplayed() {
        return title.isDisplayed();
    }

    public String getProductTitle(int index) {
        return productTitles.get(index).getText();
    }

    public List<String> getProductTitles() {
        return productTitles.stream().map(WebElement::getText).toList();
    }

    public String getProductPrice(int index) {
        return productPrices.get(index).getText();
    }

    public List<String> getProductPrices() {
        return productPrices.stream().map(WebElement::getText).toList();
    }

    public BigDecimal protoGetProductPrice(int index) {
        return new BigDecimal(productPrices.get(index).getText().substring(1));
    }

    public BigDecimal altGetProductPrice(int index) {
        String text = productPrices.get(index).getText();
        return new BigDecimal(Pattern.compile("\\d+(\\.\\d+|)").matcher(text).results().findFirst().get().group());
    }

    public List<BigDecimal> altGetProductPrices() {
        return productPrices.stream().map(WebElement::getText)
                .map(text -> new BigDecimal(Pattern.compile("\\d+(\\.\\d+|)")
                        .matcher(text).results().findFirst().get().group()))
                .toList();
    }

    public void clickAddButton(int index) {
        productAddButtons.get(index).click();
    }

    public String getAddButtonText(int index) {
        return productAddButtons.get(index).getText();
    }

    public CartPage clickCart(WebDriver driver) {
        cart.click();
        return new CartPage(driver);
    }
}
