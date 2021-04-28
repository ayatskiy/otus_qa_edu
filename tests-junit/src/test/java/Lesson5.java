import io.github.bonigarcia.wdm.WebDriverManager;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lesson5 {

    private Logger logger = LogManager.getLogger(Lesson5.class);
    private static WebDriver driver;

    @BeforeEach
    public void StartUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят, браузер готов к работе");
    }

    @AfterEach
    public void End(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void CheckDiffTwoMassage() throws InterruptedException {
        By button = By.xpath("//button[contains(text(), 'Change message')]");
        By alert = By.xpath("//ngb-alert[contains(text(), 'Message successfully changed')]");
        final String URL = "https://ng-bootstrap.github.io/#/components/alert/examples";

        driver.get(URL);
        logger.info("Сайт по URL={} открыт", URL);

        getElement(button).click();
        String firstAlertMassage = getElement(alert).getText();
        logger.info("Первое сообщение: {}", firstAlertMassage);

        Thread.sleep(1010);

        getElement(button).click();
        String secondAlertMassage = getElement(alert).getText();
        logger.info("Второе сообщение: {}", secondAlertMassage);

        Assertions.assertNotEquals(firstAlertMassage, secondAlertMassage, "Сообщения совпали!");
        logger.info("Сообщение \"{}\" изменилось на \"{}\"", firstAlertMassage, secondAlertMassage);
    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}