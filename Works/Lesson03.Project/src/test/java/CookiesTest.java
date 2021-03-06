import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

//Запустить тест командой mvn clean test с параметром -Dtest=CookiesTest.

public class CookiesTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(CookiesTest.class);

    // Читаем передаваемый параметр browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    @Test
    public void cookiesTest() {
        driver.get("https://yandex.ru/");
        logger.info("Открыта страница Yandex - " + "https://yandex.ru/");

        // Создание куки Cookie 1 и вывод информации по нему
        logger.info("Куки, которое добавили мы");
        driver.manage().addCookie(new Cookie("Cookie 1", "This Is Cookie 1"));
        Cookie cookie1 = driver.manage().getCookieNamed("Cookie 1");
        logger.info(String.format("Domain: %s", cookie1.getDomain()));
        logger.info(String.format("Expiry: %s", cookie1.getExpiry()));
        logger.info(String.format("Name: %s", cookie1.getName()));
        logger.info(String.format("Path: %s", cookie1.getPath()));
        logger.info(String.format("Value: %s", cookie1.getValue()));
        logger.info("-------------------------------------------");

        // Вывод информации по кукам yandex.ru
        logger.info("Куки, которое добавил Yandex");
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            logger.info(String.format("Domain: %s", cookie.getDomain()));
            logger.info(String.format("Expiry: %s", cookie.getExpiry()));
            logger.info(String.format("Name: %s", cookie.getName()));
            logger.info(String.format("Path: %s", cookie.getPath()));
            logger.info(String.format("Value: %s", cookie.getValue()));
            logger.info("-------------------------------------------");
        }

        // Добавляем задержку sleep чтобы увидеть результат
        waitingForAPage(1000);

    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    public static void waitingForAPage(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
