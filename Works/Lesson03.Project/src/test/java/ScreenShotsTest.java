import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Sleeper;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ScreenShotsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(ScreenShotsTest.class);

    String env = System.getProperty("browser", "edge");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    //Снятие скриншотов с помощью TakeScreenshot
    //Скриншот видимой области веб страницы
    @Test
    public void takesScreenshotViewablePageTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот видимой области веб страницы
        try {
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage linkYesImage = ImageIO.read(file);
            ImageIO.write(linkYesImage, "png", new File("temp\\TSViewablePage.png"));
            logger.info("Скриншот сохранен в файле [temp\\TSViewablePage.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(3);
    }

    //Скриншот веб элемента
    @Test
    public void takesScreenshotElementTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот веб элемента
        try {
            By divSubcategoryXPath = By.xpath("//div[@class=\"subcategory\"]");
            WebElement divSubcategory = driver.findElement(divSubcategoryXPath);
            File file = divSubcategory.getScreenshotAs(OutputType.FILE);
            BufferedImage viewablePageImage = ImageIO.read(file);
            ImageIO.write(viewablePageImage, "png", new File("temp\\TSElement.png"));
            logger.info("Скриншот сохранен в файле [temp\\TSElement.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    //Скриншот веб элемента вырезанный из видимой области веб страницы
    @Test
    public void takesScreenshotElementFromViewablePageTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот веб элемента вырезанный из видимой области веб страницы
        try {
            WebElement divSubcategory = driver.findElement(By.xpath("//div[@class=\"subcategory\"]"));
            Point location = divSubcategory.getLocation();
            Dimension size = divSubcategory.getSize();
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage viewablePageImage = ImageIO.read(file);
            BufferedImage linkYesImage = viewablePageImage.getSubimage(
                    location.getX() - 50, location.getY() - 50,
                    size.width + 200, size.height + 100);
            ImageIO.write(linkYesImage, "png", new File("temp\\TSElementFromViewablePage.png"));
            logger.info("Скриншот сохранен в файле [temp\\TSElementFromViewablePage.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    //Снятие скриншотов с помощью AShot
    //Скриншот видимой области веб страницы
    @Test
    public void aShotViewablePageTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот видимой области веб страницы
        try {
            Screenshot screenshot = new AShot().takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ASViewablePage.png"));
            logger.info("Скриншот сохранен в файле [temp\\ASViewablePage.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    //Скриншот всей веб страницы
    @Test
    public void aShotFullPageTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот всей веб страницы
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ASFullPage.png"));
            logger.info("Скриншот сохранен в файле [temp\\\\ASFullPage.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    // Скриншот веб элемента
    @Test
    public void aShotElementTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот веб элемента
        try {
            WebElement divSubcategory = driver.findElement(By.xpath("//div[@class=\"subcategory\"]"));
            Screenshot screenshot = new AShot()
                    .coordsProvider(new WebDriverCoordsProvider())
                    .takeScreenshot(driver, divSubcategory);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ASElement.png"));
            logger.info("Скриншот сохранен в файле [temp\\ASElement.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    //Сравнение скриншота с образцом
    @Test
    public void aShotCompareImagesTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сравнить скриншот с образцом
        try {
            BufferedImage actualImage = new AShot().takeScreenshot(driver).getImage();
            BufferedImage expectedImage = ImageIO.read(new File("temp\\ASCompareImagesExpectedImage.png"));
            ImageDiffer imageDiffer = new ImageDiffer();
            ImageDiff imageDiff = imageDiffer.makeDiff(actualImage, expectedImage);
            if (imageDiff.hasDiff()) {
                logger.info("Изображения одинаковые");
            } else {
                logger.info("Изображения разные");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitingForAPage(2);
    }

    //Скриншот с эффектом
    @Test
    public void aShotPrettifyTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");

        // Сделать скриншот с эффектом
        try {
            WebElement divSubcategory = driver.findElement(By.xpath("//div[@class=\"subcategory\"]"));
            Screenshot screenshot = new AShot()
                    .imageCropper(new IndentCropper()
                            .addIndentFilter(new BlurFilter()))
                    .takeScreenshot(driver, divSubcategory);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ASPrettify.png"));
            logger.info("Скриншот сохранен в файле [temp\\ASPrettify.png]");
        } catch (IOException e) {
            e.printStackTrace();
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
