import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

public class RelativeLocatorsTypesTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(RelativeLocatorsTypesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    @Test
    public void searchAboveElementTest() {
        driver.get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        // Найти элемент выше элемента
        WebElement element = driver.findElement(RelativeLocator
                .with(By.xpath("//label[text()=\"Option 1\"]"))
                .above(By.xpath("//label[text()=\"Option 2\"]")));
        logger.info("WebElement: " + element.getTagName());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchBelowElementTest() {
        driver.get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        // Найти элемент ниже элемента
        WebElement element = driver.findElement(RelativeLocator
                .with(By.xpath("//label[text()=\"Option 2\"]"))
                .below(By.xpath("//label[text()=\"Option 1\"]")));
        logger.info("WebElement: "+element.getTagName());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchToLeftOfElementTest(){
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Data-Table/index.html");

        // Найти элемент слева от элемента
        WebElement element = driver.findElement(RelativeLocator
        .with(By.xpath("//button[text()=\"Button-1\"]"))
        .toLeftOf(By.xpath("//button[text()=\"Button-3\"]")));
        logger.info("WebElement: "+ element.getTagName());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchToRightOfElementTest(){
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Data-Table/index.html");

        // Найти элемент справа от элемента
        WebElement element = driver.findElement(RelativeLocator
        .with(By.xpath("//button[text()=\"Button-3\"]"))
        .toRightOf(By.xpath("//button[text()=\"Button-1\"]")));
        logger.info("WebElement: "+element.getTagName());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchNearElementTest(){
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Data-Table/index.html");

        // Найти элемент рядом с элементом
        WebElement element = driver.findElement(RelativeLocator
        .with(By.xpath("//button[text()=\"Button-2\"]"))
        .near(By.xpath("//button[text()=\"Button-1\"]")));
        logger.info("WebElement: "+element.getTagName());
        element.click();

        waitingForAPage();
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    private void waitingForAPage() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
