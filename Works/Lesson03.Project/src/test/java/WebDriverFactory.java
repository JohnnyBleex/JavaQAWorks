import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;


public class WebDriverFactory {
    private final static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver getDriver(String browserName) {
        switch (browserName) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                chromeOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                chromeOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                chromeOptions.setAcceptInsecureCerts(false);
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--incognito");

                logger.info("Драйвер для браузера Google Chrome");
                return new ChromeDriver(chromeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                firefoxOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                firefoxOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                firefoxOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                firefoxOptions.setAcceptInsecureCerts(false);
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.addArguments("--incognito");

                logger.info("Драйвер для браузера Mozilla Firefox");
                return new FirefoxDriver(firefoxOptions);

            case "opera":
                OperaOptions operaOptions = new OperaOptions();
                WebDriverManager.operadriver().setup();
                operaOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                operaOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                operaOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                operaOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                operaOptions.addArguments("--start-maximized");
                operaOptions.addArguments("--Private");

                logger.info("Драйвер для браузера Opera");
                return new OperaDriver(operaOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                WebDriverManager.edgedriver().setup();

                edgeOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                edgeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                edgeOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                edgeOptions.setAcceptInsecureCerts(false);
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--InPrivate");
                logger.info("Драйвер для браузера Edge");
                return new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Incorrect browser name");
        }
    }
}
