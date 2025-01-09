package org.example.pop;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class ProductSubPage {
    @FindBy(css = "div[class='inventory_item_name ']")
    WebElement name;

    @FindBy(css = "div[class='inventory_item_price']")
    WebElement price;

    @FindBy(xpath = "//button[text()='Add to cart']")
    WebElement addButton;

    public ProductSubPage(WebElement webElement) {
//        PageFactory.initElements(new DefaultElementLocatorFactory(webElement), this);
        PageFactory.initElements(webElement, this);

    }
}
