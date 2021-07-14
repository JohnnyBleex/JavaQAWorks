import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

//Запустить тест командой mvn clean test с параметром -Dtest=FindElementsTest#findOneElementTest

public class FindElementsTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(FindElementsTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    @Test
    public void findOneElementTest() {
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - "
                + "https://webdriveruniversity.com/Data-Table/index.html");

        // Строка xpath запроса для поиска одного веб элемента
        String query = "//table[@id=\"t01\"]//tr[2]//td[2]";

        // Поиск одного веб элемента
        WebElement element = driver.findElement(By.xpath(query));
        logger.info("WebElement: " + element.getTagName() + " = " + element.getText());

        waitingForAPage();
    }

    @Test
    public void findManyElementsTest() {
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - "
                + "https://webdriveruniversity.com/Data-Table/index.html");

        //Строка Xpath запроса для поиска множества веб элементов
        String query = "//table[@id=\"t01\"]//tr[2]//td";

        //Поиск множества веб элементов
        List<WebElement> elements = driver.findElements(By.xpath(query));
        for (WebElement element : elements) {
            logger.info("WebElement: " + element.getTagName() + " = " + element.getText());
        }

        waitingForAPage();
    }

    @Test
    public void findOneElementInElementTest() {
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - "
                + "https://webdriveruniversity.com/Data-Table/index.html");

        //Строка Xpath запроса для поиска одного веб элемента
        String query1 = "//table[@id=\"t01\"]//tr[2]";

        //Поиск одного вэб элемента
        WebElement parentElement = driver.findElement(By.xpath(query1));
        logger.info("WebElement: " + parentElement.getTagName() + " = " + parentElement.getText());

        // Строка xpath запроса для поиска одного веб элемента в веб элементе
        String query2 = "//td[1]";

        // Поиск одного веб элемента в веб элементе
        WebElement childElement = parentElement.findElement(By.xpath(query2));
        logger.info("WebElement: " + childElement.getTagName() + " = " + childElement.getText());

        waitingForAPage();
    }

    @Test
    public void findManyElementsInElementTest() {
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - "
                + "https://webdriveruniversity.com/Data-Table/index.html");

        // Строка xpath запроса для поиска одного веб элемента
        String query1 = "//table[@id=\"t01\"]//tr[2]";

        // Поиск одного веб элемента
        WebElement parentElement = driver.findElement(By.xpath(query1));
        logger.info("WebElement: " + parentElement.getTagName() + " = " + parentElement.getText());

        // Строка xpath запроса для поиска множества веб элементов в веб элементе
        String query2 = ".//td";
        // Поиск множества веб элементов в веб элементе
        List<WebElement> childElements = parentElement.findElements(By.xpath(query2));
        for (WebElement childElement : childElements) {
            logger.info("WebElement: " + childElement.getTagName() + " = " + childElement.getText());
        }

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