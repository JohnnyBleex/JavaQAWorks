import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageLoadStrategyTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "edge");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Стратегия загрузки страницы NORMAL
    @Test
    public void pageLoadStrategyNormalTest() {
        long start = System.currentTimeMillis();
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
        WebElement element = driver.findElement(By.xpath("//a[text()=\"Магазины\"]"));
        element.click();
        long finish = System.currentTimeMillis();
        long time = finish - start;
        logger.info("Затраченное время: " + (time / 1000) + " секунд");

        waitingForAPage(5000);
    }

    //Стратегия загрузки страницы EAGER
    @Test
    public void pageLoadStrategyEagerTest(){
        long start = System.currentTimeMillis();
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
        WebElement element = driver.findElement(By.xpath("//a[text()=\"Магазины\"]"));
        element.click();
        long finish = System.currentTimeMillis();
        long time = finish - start;
        logger.info("Затраченное время: " + (time / 1000) + " секунд");

        waitingForAPage(5000);
    }

    //Стратегия загрузки страницы NONE
    @Test
    public void pageLoadStrategyNoneTest(){
        try {
            long start = System.currentTimeMillis();
            driver.get("https://www.dns-shop.ru/");
            logger.info("Открыта страница DNS - https://www.dns-shop.ru/");
            WebElement element = driver.findElement(By.xpath("//a[text()=\"Магазины\"]"));
            element.click();
            long finish = System.currentTimeMillis();
            long time = finish - start;
            logger.info("Затраченное время: " + (time / 1000) + " секунд");

        } catch (Exception e) {
            logger.info("Возникло исключение: " + e.getClass());

            if(driver != null) {
                driver.quit();
                logger.info("Драйвер остановлен!");
            }
        }
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
