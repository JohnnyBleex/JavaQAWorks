import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;

public class SwitchToTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Активный элемент
    @Test
    public void switchToActiveElementTest(){
        // Открыть страницу https://devqa.io/search/
        driver.get("https://devqa.io/search/");
        logger.info("Открыта страница devqa.io - https://devqa.io/search/");

        // Переключиться на активный элемент
        WebElement inputSearch = driver.switchTo().activeElement();
        logger.info("Переключение на активный элемент");

        // Ввести текста в поле для поиска
        inputSearch.sendKeys("Selenium");
        logger.info("Ввод текста в поле для поиска");

        waitingForAPage(5);
    }

    //Модалки
    @Test
    public void switchToAlertTest(){
        // Открыть страницу https://webdriveruniversity.com/Popup-Alerts/index.html
        driver.get("https://webdriveruniversity.com/Popup-Alerts/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Popup-Alerts/index.html");

        // Переключиться на модальное окно
        // Simple Alert
        WebElement btnSimpleAlert = driver.findElement(By.xpath("//span[@id='button1']"));
        btnSimpleAlert.click();

        waitingForAPage(3);

        // Переключение на алерт
        Alert alertSimpleAlert = driver.switchTo().alert();
        // Нажатие кнопки
        alertSimpleAlert.accept();
        logger.info("Переключение на модальное окно Simple Alert");

        waitingForAPage(3);

        // Confirmation Alert
        WebElement btnConfirmationAlert = driver.findElement(By.xpath("//span[@id='button4']"));
        btnConfirmationAlert.click();

        waitingForAPage(3);

        // Переключение на алерт
        Alert alertConfirmationAlert = driver.switchTo().alert();
        // Нажатие кнопки
        alertConfirmationAlert.accept();
        logger.info("Переключение на модальное окно Confirmation Alert");
    }

    //Фреймы
    @Test
    public void switchToFrameTest(){
        // Открыть страницу https://webdriveruniversity.com/IFrame/index.html
        driver.get("https://webdriveruniversity.com/IFrame/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/IFrame/index.html");

        // Переключиться на фрейм и нажать кнопку
        WebElement frame = driver.findElement(By.id("frame"));
        logger.info("Переключение на фрейм");
        WebElement btnInFrame = driver
                .switchTo()
                .frame(frame)
                .findElement(By.xpath("//button[@id='button-find-out-more']"));
        btnInFrame.click();

        waitingForAPage(5);
    }

    //Окна
    @Test
    public void switchToWindowTest(){
        // Открыть страницу https://devqa.io/selenium-css-selectors/
        driver.get("https://devqa.io/selenium-css-selectors/");
        logger.info("Открыта страница devqa.io - https://devqa.io/selenium-css-selectors/");
        String oldWindow = driver.getWindowHandle();
        logger.info("Старое окно:\n" + oldWindow);

        waitingForAPage(2);

        // Переключиться на новое окно
        driver.switchTo().newWindow(WindowType.WINDOW);
        logger.info("Переключение на новое окно:\n" + driver.getWindowHandle());

        waitingForAPage(2);

        // Открыть страницу https://devqa.io/selenium-tutorial/
        driver.get("https://devqa.io/selenium-tutorial/");
        logger.info("Открыта страница devqa.io - https://devqa.io/selenium-tutorial/");
        String newWindow = driver.getWindowHandle();
        logger.info("Новое окно:\n" + newWindow);

        waitingForAPage(2);

        // Переключиться на старое окно
        driver.switchTo().window(oldWindow);
        logger.info("Переключение на старое окно:\n" + driver.getWindowHandle());

        waitingForAPage(5);
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
