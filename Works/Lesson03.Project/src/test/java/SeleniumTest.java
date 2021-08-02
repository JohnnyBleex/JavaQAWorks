import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;

public class SeleniumTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(SeleniumTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Авторизация Basic Auth
    @Test
    public void basicAuthTest(){
        // Открыть страницу the-internet.herokuapp.com
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        logger.info("Открыта страница the-internet.herokuapp.com - " +
                "https://the-internet.herokuapp.com/basic_auth");

        waitingForAPage(4);

        // Открыть страницу the-internet.herokuapp.com с авторизацией
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        logger.info("Открыта страница the-internet.herokuapp.com - " +
                "https://the-internet.herokuapp.com/basic_auth");

        waitingForAPage(5);
    }

    // Отправление файла
    @Test
    public void fileUploadTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/File-Upload/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/File-Upload/index.html");

        // Приложить файл upload.txt
        WebElement fileUpload = driver.findElement(By.xpath("//input[@type=\"file\"]"));

        // !!! Поменять путь на свой
        fileUpload.sendKeys("C:\\Users\\19ser\\Desktop\\upload.txt");
        logger.info("Файл приложен");

        // Отправить файл
        WebElement btnSubmit = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        btnSubmit.click();
        logger.info("Файл отправлен!");

        waitingForAPage(4);
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
