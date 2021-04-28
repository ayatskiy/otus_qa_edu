import config.OtusWebsiteConfig;
import config.UsersData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lesson6 {

    private final OtusWebsiteConfig otusWebsite = ConfigFactory.create(OtusWebsiteConfig.class);
    private final UsersData usersData = ConfigFactory.create(UsersData.class);
    private Logger logger = LogManager.getLogger(Lesson6.class);
    private static WebDriver driver;

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
    public void CheckOtusUserData() throws InterruptedException {
        // Step 1 - Открыть https://otus.ru
        driver.get(otusWebsite.url());
        logger.info("Сайт c URL={} открыт",otusWebsite.url());
        // Step 2 - Авторизоваться на сайте
        authOtus();
        // Step 3 - Войти в личный кабинет
        jumpToOtusLK();
        // Step 4 - В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        logger.info("Очищаем старые данные");
        getElement(By.id("id_fname")).clear();
        getElement(By.id("id_fname_latin")).clear();
        getElement(By.id("id_lname")).clear();
        getElement(By.id("id_lname_latin")).clear();
        getElement(By.xpath("//input[@name='date_of_birth']")).clear();
        logger.info("Заполняем личные данные");
        getElement(By.id("id_fname")).sendKeys(usersData.rusName());
        getElement(By.id("id_fname_latin")).sendKeys(usersData.engName());
        getElement(By.id("id_lname")).sendKeys(usersData.rusSurname());
        getElement(By.id("id_lname_latin")).sendKeys(usersData.engSurname());
        getElement(By.xpath("//input[@name='date_of_birth']")).sendKeys(usersData.birth());
        //Страна
        if(!getElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText().contains(usersData.country()))
        {
            getElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).click();
            getElement(By.xpath("//*[contains(text(), '" + usersData.country() + "')]")).click();
        }
        //Город
        if(!getElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText().contains(usersData.city()))
        {
            getElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).click();
            getElement(By.xpath("//*[contains(text(), '" + usersData.city() + "')]")).click();
        }
        Thread.sleep(10000);
        //уровень англ.
        if(!getElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText().contains(usersData.engLevel()))
        {
            getElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();
            getElement(By.xpath("//*[contains(text(), '" + usersData.engLevel() + "')]")).click();
        }
        // Step 5 - Нажать сохранить
        getElement(By.xpath("//*[contains(text(), 'Сохранить и продолжить')]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("Личные данные сохранены");
        // Step 6 - Открыть https://otus.ru в “чистом браузере”
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        driver.get(otusWebsite.url());
        // Step 7 - Авторизоваться на сайте
        authOtus();
        // Step 8 - Войти в личный кабинет
        jumpToOtusLK();
        // Step 9 - Проверить, что в разделе "О себе" отображаются указанные ранее данные
        Assertions.assertAll(
                () -> Assertions.assertEquals(usersData.rusName(), driver.findElement(By.id("id_fname")).getAttribute("value")),
                () -> Assertions.assertEquals(usersData.engName(), driver.findElement(By.id("id_fname_latin")).getAttribute("value")),
                () -> Assertions.assertEquals(usersData.rusSurname(), driver.findElement(By.id("id_lname")).getAttribute("value")),
                () -> Assertions.assertEquals(usersData.engSurname(), driver.findElement(By.id("id_lname_latin")).getAttribute("value")),
                () -> Assertions.assertEquals(usersData.birth(), driver.findElement(By.xpath("//input[@name='date_of_birth']")).getAttribute("value")),
                () -> Assertions.assertEquals(usersData.country(), driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText()),
                () -> Assertions.assertEquals(usersData.city(), driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText()),
                () -> Assertions.assertEquals(usersData.engLevel(), driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText())
        );
        Thread.sleep(10000);
    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void authOtus()
    {
        getElement(By.xpath("//button[contains(@class, 'header2__auth')]")).click();
        getElement(By.xpath("//input[@placeholder='Электронная почта'][contains(@class, 'email-input')]")).sendKeys(usersData.login());
        getElement(By.xpath("//input[contains(@class, 'psw-input')]")).sendKeys(usersData.password());
        getElement(By.xpath("//div[contains(@class, 'line_relative')]/button[@type='submit']")).submit();
        logger.info("Авторизация пользователем с логином \"{}\" прошла успешно", usersData.login());
    }

    private void  jumpToOtusLK() throws InterruptedException {
        WebElement icon = getElement(By.cssSelector(".ic-blog-default-avatar"));
        Actions actions = new Actions(driver);
        actions.moveToElement(icon).build().perform();
        driver.get(otusWebsite.url_lk());
        logger.info("Открыт личный кабинет");
    }
}
