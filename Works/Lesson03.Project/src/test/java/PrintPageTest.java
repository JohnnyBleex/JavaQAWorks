import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.Sleeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class PrintPageTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(PrintPageTest.class);

    String env = System.getProperty("browser", "firefox");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Печать страницы в PDF в формате Base64
    @Test
    public void savePageToBase64TextTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Click-Buttons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Click-Buttons/index.html");

        // Сохранение страницы в PDF
        PrintsPage printer = (PrintsPage) driver;
        PrintOptions printOptions = new PrintOptions();
        printOptions.setPageRanges("1-2");
        Pdf pdf = printer.print(printOptions);
        String content = pdf.getContent();
        logger.info(content);

        waitingForAPage(4);
    }

    // Печать страницы в PDF в файл
    @Test
    public void savePageToFileTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/Click-Buttons/index.html");
        logger.info("Открыта страница webdriveruniversity.com - " +
                "https://webdriveruniversity.com/Click-Buttons/index.html");

        // Сохранение страницы в PDF
        PrintsPage printer = (PrintsPage) driver;
        PrintOptions printOptions = new PrintOptions();
        printOptions.setPageRanges("1-2");
        Pdf pdf = printer.print(printOptions);
        String content = pdf.getContent();
        byte[] decoded = java.util.Base64.getDecoder().decode(content);
        try {
            FileOutputStream fos = new FileOutputStream("temp\\pdf.pdf");
            fos.write(decoded);
            fos.flush();
            fos.close();
            logger.info("Страница сохранена в файл temp\\pdf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
