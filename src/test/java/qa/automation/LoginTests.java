package qa.automation;

import com.opencsv.exceptions.CsvException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CsvHelper;

import java.io.IOException;

public class LoginTests {
    private WebDriver driver;

    @BeforeTest
    public void initializeDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    //dataprovider1
    @DataProvider(name = "wrongUsersList")
    public Object [][] getWrongUsers(){
        return new Object[][]{
                {"standardUser7", "secret_sauce"},
                {"standard_user", "wrong password"},
                {"blah", "blah"},
                {"", "secret_sauce"}
        };
    }

    @DataProvider(name = "csvUserList")
    public static Object[][] readUsersFromCsvFile() throws IOException, CsvException {
        return CsvHelper.readCsvFile("src/test/resources/users.csv");
    }

    @Test (dataProvider = "wrongUsersList")//this is using dataprovider1
    public void UnsuccessfulLogin(String userName, String password){
        driver.get("https://www.saucedemo.com/");

        WebElement username = driver.findElement(By.id("user-name"));
        username.click();
        username.sendKeys(userName);

        //find element using xpath and indexing the results
        WebElement passwordInput = driver.findElement(By.xpath("(//input[@class='input_error form_input'])[2]"));
        passwordInput.click();
        passwordInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.cssSelector("[value=Login]"));
        loginBtn.click();

        WebElement errorLoginLabel = driver.findElement(By.xpath("//*[text()='Epic sadface: Username and password do not match any user in this service']"));

        Assert.assertTrue(errorLoginLabel.isDisplayed());
    }

    @Test(dataProvider = "csvUserList")
    public void SuccessfulLogin(String userName, String password){
        driver.get("https://www.saucedemo.com/");

        WebElement username = driver.findElement(By.id("user-name"));
        username.click();
        username.sendKeys(userName);

        //find element using xpath and indexing the results
        WebElement passwordInput = driver.findElement(By.xpath("(//input[@class='input_error form_input'])[2]"));
        passwordInput.click();
        passwordInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.cssSelector("[value=Login]"));
        loginBtn.click();

        WebElement userAllPagesButton = driver.findElement(By.id("react-burger-menu-btn"));
        Assert.assertTrue(userAllPagesButton.isDisplayed(), "This shall be visible after successful login");
    }
}
