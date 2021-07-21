import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class ImplicitWaitsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //implicitlyWait(Duration duration)
    @Test
    public void implicitlyWaitTest() {
        // Установить неявное ожидание до появления элемента на странице
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1)); // Error NoSuchElementException
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
        // Нажать на ссылку Магазины
        WebElement linkShops = driver.findElement(By.xpath("//a[text()=\"Магазины\"]"));
        linkShops.click();
        logger.info("Нажана ссылка Магазины");
        // Нажать на выпадашку Каталог
        WebElement spanCatalog = driver.findElement(By.xpath("//span[@class=\"catalog-spoiler \"]"));
        spanCatalog.click();
        logger.info("Нажата выпадашка Каталог");
        // Нажать на ссылку Бытовая техника
        WebElement linkAppliances = driver.findElement(By.xpath("//a[text()=\"Бытовая техника\"]"));
        linkAppliances.click();
        logger.info("Нажата ссылка Бытовая техника");

        waitingForAPage(5000);
    }

    //pageLoadTimeout(Duration duration)
    @Test
    public void pageLoadTimeoutTest() {
        // Установить неявное ожидание до завершения загрузки страницы
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(1)); // Error TimeoutException
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
    }

    //setScriptTimeout(Duration duration)
    @Test
    public void setScriptTimeoutTest() {
        // Установить неявное ожидание до завершения выполнения асинхронного сценария
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(1));
        //driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(0)); // Error ScriptTimeoutException
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
        ((JavascriptExecutor) driver)
                .executeAsyncScript("window.setTimeout(arguments[arguments.length-1],500)");
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
