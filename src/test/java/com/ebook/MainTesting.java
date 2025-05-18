package com.ebook;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTesting {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.navigate().to("https://ta-bookrental-fe.onrender.com/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLoginEmptyFields() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login-btn']")));
        button.click();

        WebElement error = driver.findElement(By.xpath("//*[@id=\"app\"]/div/form/div[1]"));

        assertTrue(error.isDisplayed());
    }

    @Test
    void testLoginWrongInput() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]")));
        WebElement pass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password\"]")));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login-btn']")));


        String log = "test1";
        String pas = "test122";

        login.sendKeys(log);
        pass.sendKeys(pas);
        button.click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/form/div[1]/p")));

        assertTrue(error.isDisplayed());
    }

    @Test
    void testLoginCorrectInput() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]")));
        WebElement pass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password\"]")));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login-btn']")));

        String log = "test";
        String pas = "test123";

        login.sendKeys(log);
        pass.sendKeys(pas);
        button.click();
        WebElement loggedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div")));

        assertTrue(loggedIn.isDisplayed());

    }

    @Test
    void testSignOnAlreadyCreatedUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Kliknij "Rejestracja"
        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"register-btn\"]")));
        signIn.click();

        // Poczekaj aż formularz się załaduje i dopiero wtedy pobierz pola
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password-repeat\"]")));

        String log = "test";
        String pas = "test123";

        // Za każdym razem pobierz element bezpośrednio przed użyciem
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys(log);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(pas);
        driver.findElement(By.xpath("//*[@id=\"password-repeat\"]")).sendKeys(pas);

        driver.findElement(By.xpath("//*[@id=\"register-btn\"]")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/form/div[1]/p")));
        assertTrue(error.isDisplayed());
    }

    @Test
    void testCreateNewUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Kliknij "Rejestracja"
        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"register-btn\"]")));
        signIn.click();

        // Poczekaj aż formularz się załaduje i dopiero wtedy pobierz pola
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password-repeat\"]")));

        String log = "test1";
        String pas = "test123";

        // Za każdym razem pobierz element bezpośrednio przed użyciem
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys(log);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(pas);
        driver.findElement(By.xpath("//*[@id=\"password-repeat\"]")).sendKeys(pas);

        driver.findElement(By.xpath("//*[@id=\"register-btn\"]")).click();

        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/form/div[1]/p")));
        assertTrue(success.isDisplayed());
    }
}

