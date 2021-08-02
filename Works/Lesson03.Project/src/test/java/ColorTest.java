import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;

public class ColorTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(ColorTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Получение цвета элемента
    @Test
    public void colorTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Click-Buttons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Click-Buttons/index.html");

        // Цвета
        WebElement btn = driver.findElement(By.xpath("//span[@id=\"button1\"]"));
        Color btnColor = Color.fromString(btn.getCssValue("border-bottom-color"));
        Color btnBgColor = Color.fromString(btn.getCssValue("background-color"));

        logger.info("Цвет границы кнопки: ");
        logger.info("HEX - " + btnColor.asHex());
        logger.info("RGB - "+btnColor.asRgb());
        logger.info("RGBA - "+btnColor.asRgba());

        logger.info("Цвет фона кнопки: ");
        logger.info("HEX - " + btnBgColor.asHex());
        logger.info("RGB - "+btnBgColor.asRgb());
        logger.info("RGBA - "+btnBgColor.asRgba());

        waitingForAPage(3);
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    private void waitingForAPage(int seconds) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
