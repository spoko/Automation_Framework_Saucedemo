package qa.automation;

import base.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class JavaScriptExecutorTest extends TestUtil {

    @Test
    public void LoginWithJSAction() throws InterruptedException {
        WebElement username = driver.findElement(By.id("user-name"));
        username.click();
        username.sendKeys("standard_user");

        //find element using xpath and indexing the results
        WebElement passwordInput = driver.findElement(By.xpath("(//input[@class='input_error form_input'])[2]"));
        passwordInput.click();
        passwordInput.sendKeys("secret_sauce");

        WebElement loginBtn = driver.findElement(By.cssSelector("[value=Login]"));
        loginBtn.click(); //v1
//        loginBtn.sendKeys(Keys.ENTER); //v2 submitting a form

        JavascriptExecutor script = (JavascriptExecutor) driver;
        script.executeScript("arguments[0].scrollIntoView()",
                driver.findElement(By.xpath("//a[@href='https://www.linkedin.com/company/sauce-labs/']")));
    }
}
