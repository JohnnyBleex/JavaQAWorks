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

public class NavigationTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Переход Назад на предыдущую страницу
    @Test
    public void navigateBackTest() {
        // Открыть страницу https://devqa.io/
        driver.get("https://devqa.io/");
        logger.info("Открыта страница devqa.io - https://devqa.io/");

        // Нажать на ссылку "How to Use the Linux find Command to Find Files"
        WebElement link1 = driver.findElement(By.xpath("//h2/a[@href='/linux-find-command/']"));
        link1.click();
        logger.info("Нажата ссылка \"How to Use the Linux find Command to Find Files\"");

        waitingForAPage(3);

        // Нажать на ссылку "Development"
        WebElement link2 = driver.findElement(By.xpath("//li/a[@href='/tag/featured/']"));
        link2.click();
        logger.info("Нажата ссылка \"Development\"");

        waitingForAPage(3);

        // Перейти Назад на предыдущую страницу
        driver.navigate().back();
        logger.info("Выполнен переход Назад по Истории");

        waitingForAPage(5);
    }

    //Переход Вперед на следующую страницу
    @Test
    public void navigateForwardTest(){
        // Открыть страницу https://devqa.io/
        driver.get("https://devqa.io/");
        logger.info("Открыта страница devqa.io - https://devqa.io/");

        // Нажать на ссылку "How to Use the Linux find Command to Find Files"
        WebElement link1 = driver.findElement(By.xpath("//h2/a[@href='/linux-find-command/']"));
        link1.click();
        logger.info("Нажата ссылка \"How to Use the Linux find Command to Find Files\"");

        waitingForAPage(3);

        // Нажать на ссылку "Development"
        WebElement link2 = driver.findElement(By.xpath("//li/a[@href='/tag/featured/']"));
        link2.click();
        logger.info("Нажата ссылка \"Development\"");

        waitingForAPage(3);

        // Перейти Назад на предыдущую страницу
        driver.navigate().back();
        logger.info("Выполнен переход Назад по Истории");

        waitingForAPage(3);

        // Перейти Вперед на следующую страницу
        driver.navigate().forward();
        logger.info("Выполнен переход Вперед по Истории");

        waitingForAPage(5);
    }

    //Обновление страницы
    @Test
    public void updatePageTest(){
        // Открыть страницу https://devqa.io/
        driver.get("https://devqa.io/");
        logger.info("Открыта страница devqa.io - https://devqa.io/");

        waitingForAPage(3);

        // Обновить страницу
        driver.navigate().refresh();
        logger.info("Выполнено обновление страницы");

        waitingForAPage(5);
    }

    //Загрузка новой страницы
    @Test
    public void loadNewPageTest(){
        // Открыть страницу https://devqa.io/
        driver.get("https://devqa.io/");
        logger.info("Открыта страница devqa.io - https://devqa.io/");

        waitingForAPage(3);

        // Загрузить новую страницу
        driver.navigate().to("https://devqa.io/tag/qa/");
        logger.info("Выполнена загрузка новой страницы devqa.io - https://devqa.io/tag/qa/");

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
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
