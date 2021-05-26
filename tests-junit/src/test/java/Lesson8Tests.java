import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.DrbgParameters;
import java.util.List;

class Lesson8Tests {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(Lesson8Tests.class);

    @BeforeEach
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят, браузер готов к работе");
    }

    @AfterEach
    public void End() {
        if (driver != null)
            driver.quit();
    }

    @Test
    void Alert(){
        driver.get("https://htmlweb.ru/java/js1.php");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        Alert alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(alert.getText());
        alert.accept();
        Assertions.assertEquals("Рад видеть Вас на моем сайте! Пошли дальше?", actual);
    }

    @Test
    void ConfirmAccept(){
        driver.get("https://htmlweb.ru/java/js2.php");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился confirm");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());

        alert.accept();

        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assertions.assertEquals("true", actual);
    }

    @Test
    void Confirm_Dismiss(){
        driver.get("http://subdomain.localhost/confirm.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился confirm");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.dismiss();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assertions.assertEquals("false", actual);
    }

    @Test
    void Prompt(){
        driver.get("http://subdomain.localhost/prompt.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился prompt");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.sendKeys("Anton");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.accept();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assertions.assertEquals("Hello Anton", actual);
    }

    //--------------------------------------------------------

    @Test
    void Upload(){
        String filePath = "C:\\Users\\Администратор\\Desktop\\OTUS\\Урок 1\\work1-master\\junit\\src\\test\\resources\\";
        String fileName = "ali.png";
        driver.get("http://subdomain.localhost/deletefiles.php");
        logger.info("Удалили все картинки");
        driver.get("http://subdomain.localhost/upload.php");
        driver
                .findElement(By.id("inputfile"))
                .sendKeys(filePath + fileName);
        driver.findElement(By.id("submit")).click();
        logger.info("Загрузили картинку");
        driver.get("http://subdomain.localhost/files/" + fileName);
        List<WebElement> imgs = driver.findElements(By.tagName("img"));
        Assertions.assertEquals(1, imgs.size());
    }

    //--------------------------------------------------------

    @Test
    void Iframe(){
        driver.get("http://subdomain.localhost/iframe.html");
        WebElement frame = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(frame);
        driver.switchTo().defaultContent();
        logger.info("Переходим в iframe");
        String actual = driver.findElement(By.tagName("H1")).getText();
        Assertions.assertEquals("Wee", actual);
    }

    @Test
    void Iframe2() {
        driver.get("http://subdomain.localhost/iframe2.html");
        WebElement frame = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(frame);
        logger.info(driver.findElement(By.tagName("H1")).getText());
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        logger.info(driver.findElement(By.tagName("H1")).getText());
        driver.switchTo().defaultContent();
        logger.info(driver.findElement(By.tagName("H1")).getText());

        logger.info("Переходим в iframe");
        String actual = driver.findElement(By.tagName("H1")).getText();
        Assertions.assertEquals("Wee", actual);
    }

    @Test
    void Auth(){
        driver.get("http://admin:123456@subdomain.localhost/auth/index.html");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void ProxyTest(){
        Proxy proxy = new Proxy();
        String proxyAdd = "127.0.0.1:8888";
        proxy.setHttpProxy(proxyAdd);
        proxy.setSslProxy(proxyAdd);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", proxy);
        WebDriver driver1 = new ChromeDriver(options);
        driver1.get("ya.ru");
    }
}