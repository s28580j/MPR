package com.pjatk.MPR.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddCatPageTest {
    public static final String URL = "http://localhost:8081/index/add";
    WebDriver driver ;
    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterAll
    public void tearDown() {
        driver.close();
    }


    @Test
    public void fillInAddCatForm() {
        AddCatPage addCatPage = new AddCatPage(driver);
        addCatPage.open();
        addCatPage.fillInName("name");
        addCatPage.fillInAge("1");
        addCatPage.clickSubmitButton();
        String currentUrl = driver.getCurrentUrl().split(";")[0];
        assertEquals("http://localhost:8081/index", currentUrl);
    }
}
