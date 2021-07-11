import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Запустить тест командой mvn clean test с параметром -Dtest=SimpleLocatorsTypesTest#searchByIdTest

public class SimpleLocatorsTypesTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(SimpleLocatorsTypesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    @Test
    public void searchByIdTest() {
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по атрибуту id элемента
        WebElement element = driver.findElement(By.id("FirstName"));
        logger.info("WebElement: " + element.getTagName());
        element.sendKeys("First Name");

        waitingForAPage();
    }

    @Test
    public void searchByNameTest() {
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по атрибуту name элемента
        WebElement element = driver.findElement(By.name("FirstName"));
        logger.info("WebElement: " + element.getTagName());
        element.sendKeys("First Name");

        waitingForAPage();
    }

    @Test
    public void searchByClassTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по атрибуту class элемента
        List<WebElement> elements = driver.findElements(By.className("detail_box"));
        for (WebElement element: elements){
            logger.info("WebElement: "+element.getText());
        }

        waitingForAPage();
    }

    @Test
    public void searchByLinkTextTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по тексту ссылки
        WebElement element = driver.findElement(By.linkText("LOGOUT"));
        logger.info("WebElement: "+element.getText());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchByPartialLinkTextTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по частичному тексту ссылки
        WebElement element = driver.findElement(By.partialLinkText("LOG"));
        logger.info("WebElement: "+element.getText());
        element.click();

        waitingForAPage();
    }

    @Test
    public void searchByCssSelectorTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по CSS селектору
        WebElement element1 = driver.findElement(By.cssSelector("input#Initial"));
        logger.info("WebElement: "+element1.getTagName());
        logger.info("Id: "+element1.getAttribute("id"));
        element1.sendKeys("initial");

        // Найти элемент по CSS селектору
        WebElement element2 = driver.findElement(By.cssSelector("input#FirstName"));
        logger.info("WebElement: "+element2.getTagName());
        logger.info("Id: "+element2.getAttribute("id"));
        element2.sendKeys("First Name");

        // Найти элемент по CSS селектору
        WebElement element3 = driver.findElement(By.cssSelector("input#MiddleName"));
        logger.info("WebElement: "+element3.getTagName());
        logger.info("Id: "+element3.getAttribute("id"));
        element3.sendKeys("Middle Name");

        waitingForAPage();
    }

    @Test
    public void searchByXpathQueryTest(){
        driver.get("https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");
        logger.info("Открыта страница demosite.executeautomation.com - " + "https://demosite.executeautomation.com/index.html?UserName=&Password=&Login=Login");

        // Найти элемент по XPATH запросу
        WebElement element1 = driver.findElement(By.xpath(".//input[@id='Initial']"));
        logger.info("WebElement: "+element1.getTagName());
        logger.info("Id: "+element1.getAttribute("id"));
        element1.sendKeys("Initial");

        WebElement element2 = driver.findElement(By.xpath(".//input[@id='FirstName']"));
        logger.info("WebElement: "+ element2.getTagName());
        logger.info("Id: "+element2.getAttribute("id"));
        element2.sendKeys("First Name");

        WebElement element3 = driver.findElement(By.xpath(".//input[@id='MiddleName']"));
        logger.info("WebElement: "+ element3.getTagName());
        logger.info("Id: "+element3.getAttribute("id"));
        element3.sendKeys("Middle Name");

        WebElement element4 = driver.findElement(By.xpath(".//input[@value='female']"));
        logger.info("WebElement: "+element4.getTagName());
        logger.info("Name: "+element4.getAttribute("name"));
        element4.click();

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
