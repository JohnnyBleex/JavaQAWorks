import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Sleeper;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.Duration;

public class HttpCaptureTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(HttpCaptureTest.class);

    //String env = System.getProperty("browser", "edge");

    private static BrowserMobProxyServer proxy;
    private static Proxy seleniumProxy;

    //Перехват трафика
    //Перехват трафика с помощью BrowserMob Proxy

    //Перехват трафика
    @BeforeEach
    public void setUp() {
        /*logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");*/

        // Запуск прокси BrowserMob Proxy
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.setHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.start(8000);

        // Настройка прокси Selenium
        seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Конфигурирование драйвера Selenium WebDriver для прокси
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер для браузера Google Chrome");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(capabilities);
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--ignore-urlfetcher-cert-requests");
        chromeOptions.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(chromeOptions);
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void httpCaptureTest(){
        proxy.newHar("dns-shop.ru");

        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");

        // Чтение данных прокси
        Har har = proxy.endHar();
        File harFile = new File("temp//dns-shop.har");
        try {
            har.writeTo(harFile);
        }catch (IOException e){
            e.printStackTrace();
        }
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
