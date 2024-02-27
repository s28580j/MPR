package com.pjatk.MPR.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteCatPageTest {

    public static final String URL = "http://localhost:8081/index";
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
        DeleteCatPage deleteCatPage = new DeleteCatPage(driver);
        deleteCatPage.open();
        deleteCatPage.clickSubmitButton();
        String currentUrl = driver.getCurrentUrl().split(";")[0];
        assertEquals("http://localhost:8081/index", currentUrl);
    }
}
