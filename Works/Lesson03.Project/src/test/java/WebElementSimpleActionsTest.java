import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElementSimpleActionsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Нажатие на элемент
    @Test
    public void clickTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " +
                "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        WebElement element = driver.findElement(By.name("Save"));
        // Нажатие на элемент
        element.click();

        waitingForAPage(5000);
    }

    //Ввод текста
    @Test
    public void sendKeysTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " +
                "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        WebElement element = driver.findElement(By.name("Initial"));
        // Ввод текста
        element.sendKeys("Initial");

        waitingForAPage(5000);
    }

    //Удаление текста
    @Test
    public void clearTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " +
                "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        WebElement element = driver.findElement(By.name("Initial"));
        // Удаление текста
        element.sendKeys("Initial");
        waitingForAPage(2000);
        element.clear();
        waitingForAPage(3000);
    }

    //Отправка данных формы
    @Test
    public void submitTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " +
                "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        WebElement element = driver.findElement(By.name("Initial"));
        // Отправка данных формы
        element.submit();

        waitingForAPage(3000);
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
