package broadridge;

import com.experitest.appium.SeeTestClient;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class iOS_Biometrics {

    protected IOSDriver<IOSElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient client;

    @BeforeMethod
    public void setUp() throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
        prop.load(input);

        dc.setCapability("testName", "iOS_Native_Biometrics");
        dc.setCapability("accessKey", prop.getProperty("accessKey"));
        dc.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        dc.setCapability("instrumentApp", true);
        driver = new IOSDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void testing_01() throws InterruptedException {
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();

        waitForElement(By.xpath("//*[@id='Advanced Actions' and @class='UIAStaticText']"));
        driver.findElement(By.xpath("//*[@id='Advanced Actions' and @class='UIAStaticText']")).click();

        client.setAuthenticationReply("Success", 0);

        waitForElement(By.xpath("//*[@id='Account Info' and @class='UIAStaticText']"));
        driver.findElement(By.xpath("//*[@id='Account Info' and @class='UIAStaticText']")).click();

        waitForElement(By.xpath("//*[contains(@id, 'John Doe')]"));

        driver.findElement(By.xpath("//*[@id='Back']")).click();

        client.setAuthenticationReply("PasscodeNotSetError", 0);

        waitForElement(By.xpath("//*[@id='Account Info' and @class='UIAStaticText']"));
        driver.findElement(By.xpath("//*[@id='Account Info' and @class='UIAStaticText']")).click();

        Thread.sleep(10000);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

    public void waitForElement(By by) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
    }

}
