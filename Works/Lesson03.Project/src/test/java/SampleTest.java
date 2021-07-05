import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleTest {
    protected static WebDriver driver; //объявление веб драйвера
    private Logger logger = LogManager.getLogger(SampleTest.class); //инициализация логгера

    String env = System.getProperty("browser", "chrome"); //Читаем передаваемый параметр browser (-Dbrowser)

    //Создаем новый экземпляр драйвера Google Chrome, пишем в лог.
    @BeforeEach
    public void setUp(){
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        /*WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();*/
        logger.info("Драйвер запущен!");
    }

    //Открываем https://yandex.ru/ в браузере Google Chrome, пишем в лог.
    @Test
    public void openPage() {
        driver.get("https://yandex.ru/");
        logger.info("Открыта страница Yandex - " + "https://yandex.ru/");

        // Вывод заголовка страницы
        String title = driver.getTitle();
        logger.info("title - " + title.toString());

        // Вывод текущего URL
        String currentUrl = driver.getCurrentUrl();
        logger.info("current URL - " + currentUrl.toString());

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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Закрываем все окна браузера Google Chrome, завершаем работу браузера и сервисов, освобождаем ресурсы, пишем в лог.
    @AfterEach
    public void setDown(){
        if(driver!=null){
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}
