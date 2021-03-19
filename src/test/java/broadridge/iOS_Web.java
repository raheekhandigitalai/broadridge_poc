package broadridge;

import com.experitest.reporter.testng.Listener;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileBrowserType;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Listeners(Listener.class)
public class iOS_Web {

    protected IOSDriver<IOSElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
        prop.load(input);

        dc.setCapability("testName", "iOS_Web");
        dc.setCapability("accessKey", prop.getProperty("accessKey"));
        dc.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        dc.setBrowserName(MobileBrowserType.SAFARI);
        driver = new IOSDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
    }

    @Test
    public void testing_01() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.get("https://www.google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys("Experitest");

        try {
            // There is a failure here
        } catch (Exception e) {
            // There is a success step here
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (!result.isSuccess()) {
            driver.executeScript("seetest:client.setReportStatus(\"FAILED\",\"test is FAILED\",\"a stacktrace\")");
        } else {

        }

        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
