import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;
import java.util.List;

public class JavaScriptTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(JavaScriptTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Клик на веб элемент
    @Test
    public void elementClickTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Hidden-Elements/index.html");

        // Клик по кнопке
        WebElement button1 = driver.findElement(By.xpath("//span[@id=\"button1\"]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].click();";
        js.executeScript(script, button1);
        logger.info("Нажата кнопка");

        waitingForAPage(3);
    }

    // Вывод текста элементов
    @Test
    public void elementGetTextTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Click-Buttons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Click-Buttons/index.html");

        // Вывод текста элементов
        List<WebElement> sections = driver.findElements(By.xpath("//div[@class=\"section-title\"]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "return arguments[0].innerText;";
        for (WebElement section: sections){
            String text = js.executeScript(script,section).toString();
            logger.info("Текст: \n" + text);
        }

        waitingForAPage(4);
    }

    // Скроллинг вниз
    @Test
    public void scrollPageTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Data-Table/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " + "https://webdriveruniversity.com/Data-Table/index.html");

        waitingForAPage(5);

        // Проскроллить вниз на 500px
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script1 = "window.scrollBy(0, 500);";
        js.executeScript(script1);
        logger.info("Проскроллено вниз на 500px");

        waitingForAPage(5);

        // Проскроллить вниз на 500px
        String script2 = "window.scrollBy(500, 1000);";
        js.executeScript(script2);
        logger.info("Проскроллено вниз на 500px");

        waitingForAPage(5);
    }

    // Изменение прозрачности элемента
    @Test
    public void opacityChangeTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Кнопка прозрачная");

        waitingForAPage(5);

        // Изменение прозрачности элемента
        WebElement divOpacityZero = driver.findElement(By.xpath("//div[@id=\"zero-opacity\"]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].style.opacity=1; return true;";
        js.executeScript(script, divOpacityZero);
        logger.info("Кнопка непрозрачная");

        // Клик по элементу
        divOpacityZero.click();
        logger.info("Нажата кнопка");

        waitingForAPage(5);
    }

    // Изменение видимости элемента
    @Test
    public void visibilityChangeTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Кнопка невидимая");

        waitingForAPage(5);

        // Изменение видимости элемента
        WebElement divVisibilityHidden = driver.findElement(By.xpath("//div[@id=\"visibility-hidden\"]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].style.visibility = 'visible'";
        js.executeScript(script, divVisibilityHidden);
        logger.info("Кнопка видимая");

        // Клик по элементу
        divVisibilityHidden.click();
        logger.info("Нажата кнопка");

        waitingForAPage(5);
    }

    // Изменение отображаемости элемента
    @Test
    public void displayChangeTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Кнопка неотображаемая");

        waitingForAPage(5);

        // Изменение отображаемости элемента
        WebElement divDisplayNone = driver.findElement(By.xpath("//div[@id=\"not-displayed\"]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].style.display = 'block'";
        js.executeScript(script, divDisplayNone);
        logger.info("Кнопка отображаемая");

        // Клик по элементу
        divDisplayNone.click();
        logger.info("Нажата кнопка");

        waitingForAPage(5);
    }

    // Изменение цвета элемента
    @Test
    public void bgColorChangeTest(){
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Click-Buttons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Click-Buttons/index.html");

        // Цвет кнопки до
        WebElement spanBtn = driver.findElement(By.xpath("//span[@id=\"button1\"]"));
        String colorBefore = spanBtn.getCssValue("background-color");
        logger.info("Цвет кнопки до - "+colorBefore);

        waitingForAPage(5);

        // Изменение цвета кнопки
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].style.backgroundColor = 'HotPink'";
        js.executeScript(script, spanBtn);

        // Цвет кнопки после
        String colorAfter = spanBtn.getCssValue("background-color");
        logger.info("Цвет кнопки после - " + colorAfter);

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
