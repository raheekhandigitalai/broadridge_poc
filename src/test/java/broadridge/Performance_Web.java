package broadridge;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

// https://docs.experitest.com/display/TE/Grid+-+Performance+Transaction+Commands
public class Performance_Web {

    private static final String ACCESS_KEY = "eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk";
    private RemoteWebDriver driver;
    private DesiredCapabilities dc = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws Exception {
        dc.setCapability("testName", "Performance_Web");
        dc.setCapability("accessKey", ACCESS_KEY);
        dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        driver = new RemoteWebDriver(new URL("https://uscloud.experitest.com/wd/hub"), dc);
    }

    @Test
    public void browserTestGoogleSearch() {
        driver.executeScript("seetest:client.startPerformanceTransaction(\"1.3\")");
        driver.get("https://www.google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        Object navigate = driver.executeScript("seetest:client.endPerformanceTransaction(\"Navigating to Google\")");
        System.out.println(navigate.toString());

        driver.executeScript("seetest:client.startPerformanceTransaction(\"1.3\")");
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.click();
        searchBar.sendKeys("Experitest");
        searchBar.sendKeys(Keys.ENTER);
        Object search = driver.executeScript("seetest:client.endPerformanceTransaction(\"Performing Search\")");
        System.out.println(search.toString());
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

}
