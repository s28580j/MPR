package com.pjatk.MPR.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditCatPage {
    public static final String URL = "http://localhost:8081/index";

    WebDriver driver;
    @FindBy(id="update")
    WebElement updateButton;
    @FindBy(id="age")
    WebElement catAge;
    @FindBy(id="submit-button")
    WebElement submitButton;

    public EditCatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open(){
        driver.get(URL);

    }
    public void openUpdateButton() {
        this.updateButton.click();
    }
    public void fillInAge(String age) {
        this.catAge.clear();
        this.catAge.sendKeys(age);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

}
