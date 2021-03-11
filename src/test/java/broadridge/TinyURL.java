package broadridge;

import com.experitest.appium.SeeTestClient;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Point;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class TinyURL {

    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient client;

    @BeforeMethod
    public void setUp() throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
        prop.load(input);

        dc.setCapability("testName", "tinyUrlVerification");
        dc.setCapability("accessKey", prop.getProperty("accessKey"));
        dc.setCapability("dontGoHomeOnQuit", true);
        driver = new AndroidDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void extract_url_from_text() {
        client.launch("com.google.android.apps.googlevoice/.SplashActivity", false, false);
        driver.context("NATIVE_APP");

        AndroidElement element = driver.findElement(By.xpath("//*[@id='message_text' and contains(text(), 'cnn')]"));
        String text = element.getAttribute("text");
        String[] list = text.split(":");
        String url = list[1].trim();

        System.out.println(url);
    }

    @Test
    public void click_on_url_based_on_coordinates() throws InterruptedException {
        client.launch("com.google.android.apps.googlevoice/.SplashActivity", false, false);
        driver.context("NATIVE_APP");

        AndroidElement element = driver.findElement(By.xpath("//*[@id='message_text' and contains(text(), 'cnn')]"));

//        int x = element.getLocation().x;
//        int y = element.getLocation().y;
//        System.out.println(x);
//        System.out.println(y);
        // 680, 1840

        driver.performTouchAction(new TouchAction(driver).tap(new TapOptions().withPosition(new PointOption().withCoordinates(645, 1064)))).perform();
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
