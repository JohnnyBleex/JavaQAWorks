import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LogsTest2 {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(LogsTest2.class);

    String env = System.getProperty("browser", "edge");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Доступные типы логов
    @Test
    public void availableLogTypesTest() {
        // Получение доступных типов логов
        Set<String> availableLogTypes = driver.manage().logs().getAvailableLogTypes();
        for (String s : availableLogTypes) {
            logger.info("Log typ: " + s);
        }

        waitingForAPage(1);
    }

    //Получение логов заданного типа
    @Test
    public void logsByTypeTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        // Нажать на ссылку "Да"
        WebElement linkYes = driver.findElement(By.xpath("//a[text()='Да']"));
        linkYes.click();
        logger.info("Нажата ссылка 'Да'");

        // Нажать на ссылку "Бытовая техника"
        WebElement linkBT = driver.findElement(By.xpath("//div/a[text()='Бытовая техника']"));
        linkBT.click();
        logger.info("Нажата ссылка 'Бытовая техника'");

        // Получить логи
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        List<LogEntry> logEntriesList = logEntries.getAll();
        for (LogEntry logsEntry : logEntriesList) {
            logger.info(Date.from(Instant.ofEpochSecond(logsEntry.getTimestamp())) + " " +
                    logsEntry.getLevel() + " " + logsEntry.getMessage());
        }

        waitingForAPage(3);
    }

    //Получение логов заданного типа и уровня
    @Test
    public void logsByTypeAndLevelTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        WebElement linkYes = driver.findElement(By.xpath("//a[text()='Да']"));
        linkYes.click();
        logger.info("Нажата ссылка 'Да'");

        // Нажать на ссылку "Бытовая техника"
        WebElement linkBT = driver.findElement(By.xpath("//div/a[text()='Бытовая техника']"));
        linkBT.click();
        logger.info("Нажата ссылка 'Бытовая техника'");

        // Получить логи
        Logs logs = driver.manage().logs();
        LogEntries logsEntries = logs.get(LogType.BROWSER);
        List<LogEntry> logsEntriesList = logsEntries.getAll().stream()
                .filter(a -> a.getLevel() == Level.SEVERE)
                .collect(Collectors.toList());
        for (LogEntry logsEntry : logsEntriesList) {
            logger.info(Date.from(Instant.ofEpochMilli(logsEntry.getTimestamp())) + " " +
                    logsEntry.getLevel() + " " + logsEntry.getMessage());
        }

        waitingForAPage(2);
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
