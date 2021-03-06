import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

//Запустить тест командой mvn clean test с параметром -Dtest=BrowserWindowsTest

public class BrowserWindowsTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(BrowserWindowsTest.class);

    String env = System.getProperty("browser", "opera");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void browserWindowsTest(){
        // Отображение окна браузера в полноэкранном режиме
        driver.get("https://yandex.ru/");
        driver.manage().window().fullscreen();
        logger.info("Открыта страница Yandex - " + "https://yandex.ru/");

        // Отображение размеров окна браузер
        logger.info(String.format("Browser Window Height: %d", driver.manage().window().getSize().getHeight()));
        logger.info(String.format("Browser Window Width: %d", driver.manage().window().getSize().getWidth()));

        // Добавляем задержку sleep чтобы увидеть результат
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}
