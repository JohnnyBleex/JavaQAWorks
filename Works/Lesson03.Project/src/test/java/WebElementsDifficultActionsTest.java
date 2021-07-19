import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WebElementsDifficultActionsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Левый клик мышью в текущей позиции
    @Test
    public void clickTest() {
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Левый клик мышью в текущей позиции
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .click()
                .perform();
        String text = element.getText();
        logger.info("Text: " + text);
        Assertions.assertTrue(text.equals("Dont release me!!!"));

        waitingForAPage(5000);
    }

    //Левый клик мышью в центре элемента
    @Test
    public void clickTargetTest() {
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Левый клик мышью в центре элемента
        Actions actions = new Actions(driver);
        actions.click(element)
                .perform();
        String text = element.getText();
        logger.info("Text: "+text);
        Assertions.assertTrue(text.equals("Dont release me!!!"));

        waitingForAPage(4000);
    }

    //Левый клик мышью c удержанием в текущей позиции
    @Test
    public void clickAndHoldTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Левый клик мышью c удержанием в текущей позиции
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .clickAndHold()
                .perform();
        String text = element.getText();
        logger.info("Text: "+text);
        Assertions.assertTrue(text.equals("Well done! keep holding that click now....."));

        waitingForAPage(5000);
    }

    //Левый клик мышью c удержанием в центре элемента
    @Test
    public void clickAndHoldTargetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Левый клик мышью c удержанием в текущей позиции
        Actions actions = new Actions(driver);
        actions.clickAndHold(element)
                .perform();
        String text = element.getText();
        logger.info("Text: "+text);
        Assertions.assertTrue(text.equals("Well done! keep holding that click now....."));

        waitingForAPage(5000);
    }

    //Правый клик мышью в текущей позиции
    @Test
    public void contextClickTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Правый клик мышью в текущей позиции
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .contextClick()
                .perform();

        waitingForAPage(5000);
    }

    //Правый клик мышью в центре элемента
    @Test
    public void contextClickTargetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Правый клик мышью в текущей позиции
        Actions actions = new Actions(driver);
        actions.contextClick(element)
                .perform();

        waitingForAPage(5000);
    }

    //Двойной клик мышью в текущей позиции
    @Test
    public void doubleClickTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("double-click"));
        // Двойной клик мышью в текущей позиции
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .doubleClick()
                .perform();

        waitingForAPage(5000);
    }

    //Двойной клик мышью в центре элемента
    @Test
    public void doubleClickTargetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("double-click"));
        // Двойной клик мышью в центре элемента
        Actions actions = new Actions(driver);
        actions.doubleClick(element)
                .perform();
        String color = element.getCssValue("background-color");
        logger.info("Color: " + color);
        Assertions.assertTrue(color.equals("rgba(147, 203, 90, 1)"));

        waitingForAPage(5000);
    }

    //Перетаскивание объекта
    @Test
    public void dragAndDropTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        // Перетаскивание объекта
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source,target)
                .perform();
        String text = target.getText();
        logger.info("Text: " + text);
        Assertions.assertTrue(text.equals("Dropped!"));

        waitingForAPage(5000);
    }

    //Перетаскивание объекта со смещением
    @Test
    public void dragAndDropByTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        // Перетаскивание объекта со смещением
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(source, 90,90)
                .perform();
        String text = target.getText();
        logger.info("Text: " + text);
        Assertions.assertTrue(text.equals("Dropped!"));

        waitingForAPage(5000);
    }

    //Нажатие и отпускание клавиши клавиатуры
    @Test
    public void keyDownKeyUpTest(){
        driver.get("https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        WebElement element = driver.findElement(By.xpath("//input[@id='myInput']"));
        //element.sendKeys("kiwi");
        // Нажатие и отпускание клавиши клавиатуры
        Actions actions = new Actions(driver);
        actions
                .moveToElement(element)
                .keyDown(Keys.SHIFT)
                .sendKeys("kiwi")
                .keyUp(Keys.SHIFT)
                .perform();

        waitingForAPage(5000);
    }

    //Нажатие и отпускание клавиши клавиатуры в элементе
    @Test
    public void keyDownKeyUpTargetTest(){
        driver.get("https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        WebElement element = driver.findElement(By.id("myInput"));
        // Нажатие и отпускание клавиши клавиатуры в элементе
        Actions actions = new Actions(driver);
        actions.keyDown(element, Keys.SHIFT)
                .sendKeys(element,"kiwi")
                .keyUp(element,Keys.SHIFT)
                .perform();

        waitingForAPage(5000);
    }

    //Смещение мыши
    @Test
    public void moveByOffsetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.xpath("//button[text()='Hover Over Me First!']"));
        // Смещение мыши
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .moveByOffset(0,30)
                .click()
                .perform();

        waitingForAPage(5000);
    }

    //Перемещение мыши в центр элемента
    @Test
    public void moveToElementTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.xpath("//button[text()='Hover Over Me Second!']"));
        // Перемещение мыши в центр элемента
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .perform();

        waitingForAPage(5000);
    }

    //Перемещение мыши в центр элемента со смещением
    @Test
    public void moveToElementOffsetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.xpath("//button[text()='Hover Over Me Second!']"));
        // Перемещение мыши в центр элемента со смещением
        Actions actions = new Actions(driver);
        actions.moveToElement(element, 0,10)
                .perform();

        waitingForAPage(5000);
    }

    //Отпускание левой кнопки мыши
    @Test
    public void releaseTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Отпускание левой кнопки мыши
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .clickAndHold()
                .pause(600)
                .release()
                .perform();

        waitingForAPage(5000);
    }

    //Отпускание левой кнопки мыши в центре элемента
    @Test
    public void releaseTargetTest(){
        driver.get("https://webdriveruniversity.com/Actions/index.html#");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Actions/index.html#");
        WebElement element = driver.findElement(By.id("click-box"));
        // Отпускание левой кнопки мыши в центре элемента
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .clickAndHold()
                .pause(600)
                .release(element)
                .perform();

        waitingForAPage(5000);
    }

    //

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
