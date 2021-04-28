import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Lesson7 {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(Lesson7.class);

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
    public void manageCookie(){
        driver.get("https://otus.ru/");
        //Добавить Cookie#1 с параметром Otus1 и значением Value1
        driver.manage().addCookie(new Cookie("Otus1", "Value1"));
        //Добавить Cookie#2 с параметром Otus2 и значением Value2
        driver.manage().addCookie(new Cookie("Otus2", "Value2"));
        //Добавить Cookie#3 с параметром Otus3 и значением Value3 (добавлять через переменную, переменная должна быть сохранена)
        Cookie cookie = new Cookie("Otus3", "Value3");
        driver.manage().addCookie(cookie);
        //Добавить Cookie#4 с параметром Otus4 и значением Value4
        driver.manage().addCookie(new Cookie("Otus4", "Value4"));

        //Вывести на экран все Cookies
        logger.info(driver.manage().getCookies());
        //Вывести на экран Cookie1
        logger.info(driver.manage().getCookieNamed("Otus1"));
        //Удалить Cookie#2 по имени куки
        driver.manage().deleteCookieNamed("Otus2");
        //Удалить Cookie#3 по переменной Cookie
        driver.manage().deleteCookie(cookie);
        //Удалить все куки, убедиться что их нет
        driver.manage().deleteAllCookies();
        Assertions.assertEquals(0, driver.manage().getCookies().size());
    }

    @Test
    public void waitSample(){
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(0,TimeUnit.SECONDS);
        driver.get("https://otus.ru/");
    }

    @Test
    public void windows() throws InterruptedException {
        //Группа А: Запустить тест в полном окне («не киоск»), получить его размер
        driver.manage().window().maximize();
        logger.info(driver.manage().window().getSize());
        Thread.sleep(1000);
        //Группа Б: Запустить тест в расширении 800 на 600, получить его позицию
        driver.manage().window().setSize(new Dimension(800,600));
        logger.info(driver.manage().window().getPosition());
        Thread.sleep(1000);
        //Группа В: Тоже, что группа Б + передвинуть браузер по квадрату (четырем точкам)
        driver.manage().window().setSize(new Dimension(800,600));
        Point point = driver.manage().window().getPosition();

        point.x = point.x + 50000;
        point.y = point.y;
        driver.manage().window().setPosition(point);
        Thread.sleep(1000);

        point.x = point.x;
        point.y = point.y + 50000;
        driver.manage().window().setPosition(point);
        Thread.sleep(1000);

        point.x = point.x - 50000;
        point.y = point.y;
        driver.manage().window().setPosition(point);
        Thread.sleep(1000);

        point.x = point.x;
        point.y = point.y - 50000;
        driver.manage().window().setPosition(point);
        Thread.sleep(1000);
    }

    @Test
    public void headless(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);

    }
}
