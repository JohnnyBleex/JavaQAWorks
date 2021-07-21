import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomExpectedConditionTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Именованный класс
    static class AlertExpectedCondition implements ExpectedCondition<Alert> {
        @Override
        public Alert apply(WebDriver driver) {
            try {
                assert driver != null;
                return driver.switchTo().alert();
            } catch (NoAlertPresentException ex) {
                return null;
            }
        }
    }
    @Test
    public void namedClassTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Popup-Alerts/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Popup-Alerts/index.html");

        // Нажать на кнопку [CLICK ME!] в блоке [JavaScript Alert]
        By spanJSAlertXpath = By.xpath("//span[@id='button1']");
        WebElement spanJSAlert = driver.findElement(spanJSAlertXpath);
        spanJSAlert.click();
        logger.info("Нажата кнопка [CLICK ME!] в блоке [JavaScript Alert]");

        // Подождать пока появится алерт
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(new AlertExpectedCondition());
        logger.info("Алерт отобразился!");

        waitingForAPage(5000);
    }

    //Анонимный класс
    @Test
    public void anonymousClassTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Popup-Alerts/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Popup-Alerts/index.html");

        // Нажать на кнопку [CLICK ME!] в блоке [JavaScript Alert]
        driver.findElement(By.xpath("//span[@id='button1']")).click();
        logger.info("Нажата кнопка [CLICK ME!] в блоке [JavaScript Alert]");

        // Подождать пока появится алерт
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(
                // Анонимный класс new ExpectedCondition<Alert>()
                new ExpectedCondition<Alert>() {
                    @Override
                    public Alert apply(WebDriver input) {
                        try {
                            return driver.switchTo().alert();
                        } catch (NoAlertPresentException e) {
                            return null;
                        }
                    }
                });
        logger.info("Алерт отобразился!");

        waitingForAPage(5000);
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    private void waitingForAPage(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
