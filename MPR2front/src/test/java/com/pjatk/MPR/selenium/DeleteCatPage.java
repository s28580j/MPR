package com.pjatk.MPR.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteCatPage {
    public static final String URL = "http://localhost:8081/index";

    WebDriver driver;
    @FindBy(id="delete")
    WebElement deleteButton;

    public DeleteCatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void open(){
        driver.get(URL);

    }
    public void clickSubmitButton() {
        deleteButton.click();
    }

}
