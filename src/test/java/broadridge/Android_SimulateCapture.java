package broadridge;

import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
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
import java.net.URL;
import java.util.Properties;

public class Android_SimulateCapture {

    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient client;

    @BeforeMethod
    public void setUp() throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
        prop.load(input);

        dc.setCapability("testName", "Android_Native");
        dc.setCapability("accessKey", prop.getProperty("accessKey"));
        dc.setCapability("deviceQuery", "@os='android' and @category='PHONE'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.uicatalog/.MainActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        dc.setCapability("instrumentApp", true);
        driver = new AndroidDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void testing_01() throws InterruptedException {
        client.swipe("Down", 500, 500);

        waitForElement(By.xpath("//*[@id='text1' and @text='Camera']"));
        driver.findElement(By.xpath("//*[@id='text1' and @text='Camera']")).click();

        for (int i = 0; i < 2; i++) {
            waitForElement(By.xpath("//*[@id='permission_allow_button']"));
            driver.findElement(By.xpath("//*[@id='permission_allow_button']")).click();
            Thread.sleep(3000);
        }

        waitForElement(By.xpath("//*[@id='scanner3']"));
        driver.findElement(By.xpath("//*[@id='scanner3']")).click();

        client.simulateCapture(System.getProperty("user.dir") + "\\images\\cheque.jpeg");

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
