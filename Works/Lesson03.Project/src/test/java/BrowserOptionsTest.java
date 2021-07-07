import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

//Запустить тест командой mvn clean test с параметром -Dtest=BrowserOptionsTest

public class BrowserOptionsTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(BrowserOptionsTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp(){
        driver = getDriver();
        logger.info("Драйвер запущен");
    }

    @Test
    public void browserOptionsTest(){
        driver.get("https://yandex.ru/");
        logger.info("Открыта страница Yandex - " + "https://yandex.ru/");

        // Ввод текста для поиска
        String searchInputXpath = ".//input[@class='input__control input__input mini-suggest__input']";
        WebElement searchInput = driver.findElement(By.xpath(searchInputXpath));
        String searchText = "Java";
        searchInput.sendKeys(searchText);

        // Нажатие кнопка "Найти"
        String searchButtonXpath = ".//button[@class='button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited']";
        WebElement searchButton = driver.findElement(By.xpath(searchButtonXpath));
        searchButton.click();

        // Добавляем задержку sleep чтобы увидеть результат
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void setDown(){
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    public WebDriver getDriver(){
        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
        options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
        options.setAcceptInsecureCerts(false);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Добавление аргументов запуска Google Chrome
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");

        return new ChromeDriver(options);
    }
}
