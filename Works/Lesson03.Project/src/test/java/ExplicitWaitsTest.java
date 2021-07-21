import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ExplicitWaitsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebElementPropertiesTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Атрибуты и свойства элементов
    @Test
    public void elementAttributeExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Accordion/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Accordion/index.html");

        // Раскрыть гармошку
        // Кликнуть на кнопку [Manual Testing]
        WebElement btnManualTesting = driver.findElement(By.xpath("//button[text()='Manual Testing']"));
        btnManualTesting.click();

        // Подождать пока не покажется блок с текстом
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement divManualTesting = driver
                .findElement(By.xpath("//button[text()=\"Manual Testing\"]/following-sibling::div[1]"));
        wait.until(ExpectedConditions.attributeToBe(divManualTesting, "style", "max-height: 70px;"));

        logger.info("Гармошка раскрыта!");

        waitingForAPage(7000);
    }

    //Состояние элементов
    @Test
    public void elementStateExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        logger.info("Открыта страница webdriveruniversity.com -" +
                " https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        // Подождать пока чекбокс [Option 1] не станет кликабельным
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement chbOption1 = driver.findElement(By.xpath("//input[@value='option-1']"));
        wait.until(ExpectedConditions.elementToBeClickable(chbOption1));

        // Проставить чекбокс [Option 1]
        chbOption1.click();

        logger.info("Чекбокс проставлен!");

        waitingForAPage(8000);
    }

    //Количество элементов
    @Test
    public void elementsNumberExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        // Подождать пока количество чекбоксов имеющих текст [Option] не будет 4
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        By chbOptionXpath = By.xpath("//label[contains(text(),'Option')]");
        wait.until(ExpectedConditions.numberOfElementsToBe(chbOptionXpath, 4));

        // Подсчитать количество чекбоксов имеющих текст [Option]
        List<WebElement> chbOption = driver.findElements(chbOptionXpath);
        int count = chbOption.size();
        logger.info("Чекбоксов имеющих текст [Option]: " + count);

        waitingForAPage(6000);
    }

    //Наличие элементов
    @Test
    public void elementPresenceExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");

        // Ввести текст в поле ввода [Food Item]
        By tbxFoodItemXpath = By.xpath("//div[@class='autocomplete']/input");
        WebElement tbxFoodItem = driver.findElement(tbxFoodItemXpath);
        tbxFoodItem.sendKeys("Ca");

        // Подождать пока отобразятся подсказки
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        By divAutoCompleteXpath = By.xpath("//div/input[@type='hidden']");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(divAutoCompleteXpath));

        // Вывести в лог содержимое подсказок
        List<WebElement> divAutoComplete = driver.findElements(divAutoCompleteXpath);
        for (WebElement element : divAutoComplete) {
            logger.info("Подсказка: " + element.getAttribute("value"));
        }

        waitingForAPage(6000);
    }

    //Отсутствие элементов
    @Test
    public void elementStalenessExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Autocomplete-TextField/autocomplete-textfield.html");

        // Ввести текст в поле ввода [Food Item]
        WebElement tbxFoodItem = driver.findElement(By.xpath("//div[@class='autocomplete']/input"));
        tbxFoodItem.sendKeys("Ca");

        // Подождать пока отобразятся подсказки
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        By divCabbageXpath = By.xpath("//input[@value='Cabbage']//parent::div");
        wait.until(ExpectedConditions.presenceOfElementLocated(divCabbageXpath));

        // Найти подсказку Cabbage
        WebElement divCabbage = driver.findElement(divCabbageXpath);
        divCabbage.click();

        // Очистить поле ввода [Food Item]
        tbxFoodItem.clear();

        // Ввести текст в поле ввода [Food Item]
        tbxFoodItem.sendKeys("Ba");

        // Подождать отсутствие подсказки Cabbage
        wait.until(ExpectedConditions.stalenessOf(divCabbage));

        waitingForAPage(6000);
    }

    //Видимость элементов
    @Test
    public void elementVisibilityExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        // Нажать на выпадашку [JAVA]
        WebElement selProgLangs = driver.findElement(By.xpath("//div/select[@id='dropdowm-menu-1']"));
        selProgLangs.click();
        logger.info("Нажата выпадашка [JAVA]");

        // Подождать пока отобразится вариант для выбора [Python] в выпадашке [JAVA]
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        By optJavaXpath = By.xpath("//select/option[@value='python']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(optJavaXpath));
        logger.info("Отображается вариант для выбора [Python]");

        // Выбрать вариант для выбора [Python]
        WebElement optJava = driver.findElement(optJavaXpath);
        optJava.click();
        logger.info("Выбран вариант для выбора [Python]");

        waitingForAPage(5000);
    }

    //Невидимость элементов
    @Test
    public void elementInvisibilityExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Hidden-Elements/index.html");

        // Подождать что не отобразится элемент [Not Displayed]
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        By divSpanXpath = By.xpath("//div/span[1]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(divSpanXpath));
        logger.info("Не отображается элемент [Not Displayed]");

        waitingForAPage(5000);
    }

    //Текстовое содержимое элементов
    @Test
    public void elementTextExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Accordion/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Accordion/index.html");

        // Вывести текст в блоке с @id="text-appear-box"
        By divPXpath = By.xpath("//div[@id='text-appear-box']/p");
        WebElement divP = driver.findElement(divPXpath);
        logger.info("Текст в блоке @id=\"text-appear-box\": " + divP.getText());

        // Подождать пока смениться текст в блоке
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.textToBe(divPXpath, "LOADING COMPLETE."));

        WebElement element = driver.findElement(By.xpath("//button[@id='click-accordion']"));
        element.click();

        // Вывести текст в блоке с @id="text-appear-box"
        logger.info("Текст в блоке @id=\"text-appear-box\": " + divP.getText());

        waitingForAPage(500);
    }

    //Работа со страницей
    @Test
    public void pageExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/index.html");
        logger.info("Открыта страница webdriveruniversity.com - https://webdriveruniversity.com/index.html");

        // Подождать пока появится заголовок страницы
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.titleContains("WebDriverUniversity.com"));

        // Вывести текст в блоке с @id="text-appear-box"
        logger.info("Заголовок страницы: " + driver.getTitle());

        waitingForAPage(5000);
    }

    //Работа с алертами
    @Test
    public void alertExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Popup-Alerts/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Popup-Alerts/index.html");

        // Нажать на кнопку [CLICK ME!] в блоке [JavaScript Alert]
        By spanJSAlertXpath = By.xpath("//span[@id='button1']");
        WebElement spanJSAlert = driver.findElement(spanJSAlertXpath);
        spanJSAlert.click();
        logger.info("Нажата кнопка [CLICK ME!] в блоке [JavaScript Alert]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Алерт отобразился!");

        waitingForAPage(5000);
    }

    //Работа с фреймами
    @Test
    public void frameExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/IFrame/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/IFrame/index.html");

        // Подождать доступность фрейма и переключиться на него
        WebElement iFrame = driver.findElement(By.xpath("//iframe"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
        logger.info("Выполнено переключение на фрейм");

        waitingForAPage(5000);
    }

    //Работа с окнами
    @Test
    public void windowsExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/index.html");

        // Подождать когда будет открыто одно окно
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        logger.info("Открыто одно окно");

        waitingForAPage(5000);
    }

    //Работа с JS скриптом
    @Test
    public void jsScriptExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/index.html");

        // Подождать выполнения JS скрипта
        String jsScript = "window.setTimeout(arguments[arguments.length - 1], 500);";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(jsScript));
        logger.info("Выполнение JS скрипта успешно");

        waitingForAPage(5000);
    }

    //Работа с логическими функциями
    @Test
    public void logicalExpectedConditionTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/IFrame/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/IFrame/index.html");

        // Подождать доступность фрейма и переключиться на него
        By iframeXpath = By.xpath("//iframe");
        WebElement iframe = driver.findElement(iframeXpath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.numberOfWindowsToBe(1),
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe)));
        logger.info("Открыто одно окно и выполнено переключение на фрейм");

        waitingForAPage(5000);
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
