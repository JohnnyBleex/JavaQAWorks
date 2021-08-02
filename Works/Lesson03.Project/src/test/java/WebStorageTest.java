import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;
import java.util.Set;

public class WebStorageTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(WebStorageTest.class);

    String env = System.getProperty("browser", "firefox");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Локальное хранилище
    @Test
    public void localStorageTest() {
        // Открыть страницу dns-shop.ru
        driver.get("https://www.dns-shop.ru");
        logger.info("Открыта страница dns-shop.ru - " + "https://www.dns-shop.ru");

        // Получить доступ к локальному хранилищу
        WebStorage webStorage = (WebStorage) driver;
        LocalStorage localStorage = webStorage.getLocalStorage();

        // Получить данные локального хранилища
        Set<String> keySet = localStorage.keySet();
        logger.info("Получение данных локального хранилища dns-shop.ru");
        for (String key : keySet) {
            logger.info("Item [" + key + "]: " + localStorage.getItem(key));
        }

        // Добавить данные в локальное хранилище
        localStorage.setItem("item1", "item1");
        logger.info("Добавление данных в локальное хранилище dns-shop.ru");
        logger.info("Item [item1]: " + localStorage.getItem("item1"));

        // Удалить данные из локального хранилища
        localStorage.removeItem("item1");
        logger.info("Удаление данных из локального хранилища dns-shop.ru");
        logger.info("Item [item1]: " + localStorage.getItem("item1"));

        waitingForAPage(3);
    }

    // Сессионное хранилище
    @Test
    public void sessionStorageTest() {
        // Открыть страницу dns-shop.ru
        driver.get("https://www.dns-shop.ru");
        logger.info("Открыта страница dns-shop.ru - " + "https://www.dns-shop.ru");

        // Получить доступ к сессионному хранилищу
        WebStorage webStorage = (WebStorage) driver;
        SessionStorage sessionStorage = webStorage.getSessionStorage();

        // Получить данные сессионного хранилища
        Set<String> keySet = sessionStorage.keySet();
        logger.info("Получение данных сессионного хранилища dns-shop.ru");
        for (String key : keySet) {
            logger.info("Item [" + key + "]: " + sessionStorage.getItem(key));
        }

        // Добавить данные в сессионное хранилище
        sessionStorage.setItem("item1", "item1");
        logger.info("Добавление данных в сессионное хранилище dns-shop.ru");
        String item1 = sessionStorage.getItem("item1");
        logger.info("Item [item1]: " + item1);

        // Удалить данные из локального хранилища
        sessionStorage.removeItem("item1");
        logger.info("Удаление данных из сессионного хранилища dns-shop.ru");
        logger.info("Item [item1]: " + sessionStorage.getItem("item1"));

        // Получить данные сессионного хранилища в новой вкладке
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.dns-shop.ru");
        logger.info("Открыта страница dns-shop.ru - " + "https://www.dns-shop.ru");
        Set<String> keySet2 = sessionStorage.keySet();
        logger.info("Получение данных сессионного хранилища dns-shop.ru в новой вкладке");
        for (String key : keySet2) {
            logger.info("Item [" + key + "]: " + sessionStorage.getItem(key));
        }

        waitingForAPage(2);
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
