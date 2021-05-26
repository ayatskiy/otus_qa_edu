import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


class Lesson10BonusTests {

    @Test
    void properties(){
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("other_configs")).getPath();
        String appConfigPath = rootPath + "/sameProperties.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property1 = appProps.getProperty("prop1");
        Assertions.assertEquals("value1", property1);
    }

    @Test
    void propertiesXML(){
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("other_configs")).getPath();
        String iconConfigPath = rootPath + "/prop.xml";
        Properties iconProps = new Properties();
        try {
            iconProps.loadFromXML(new FileInputStream(iconConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals("icon1.jpg", iconProps.getProperty("prop1"));
    }

    @Test
    void softAssert(){
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("other_configs")).getPath();
        String appConfigPath = rootPath + "/sameProperties.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(appProps.getProperty("prop1")).isEqualTo("value1");
        softAssertions.assertThat(appProps.getProperty("prop2")).isEqualTo("value2");
        softAssertions.assertThat(appProps.getProperty("prop3")).isEqualTo("value3");
        softAssertions.assertAll();
    }

    @Test
    void partAssert(){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "sameProperties.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(appProps.getProperty("prop3").contains("vaue"));
    }

    @Test
    void cookie(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://otus.ru/");
        //driver.manage().addCookie(new Cookie("auth_token_expires","1715339163"));
        //driver.manage().addCookie(new Cookie("auth_token","-LdpHug9FCzV9lFEtCZlRw"));
        //driver.manage().addCookie(new Cookie("oid","31c496ae747c4c3c2018348af56769f5"));
        driver.manage().addCookie(new Cookie("sessionid","qlsgfv3g1x444cfcb2e9tg6i65fq4fqi"));

        driver.get("https://otus.ru/learning/");
    }
}
