import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.browser.Browser;
import org.openqa.selenium.devtools.v91.browser.model.PermissionType;
import org.openqa.selenium.devtools.v91.emulation.Emulation;
import org.openqa.selenium.devtools.v91.fetch.Fetch;
import org.openqa.selenium.devtools.v91.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v91.fetch.model.RequestId;
import org.openqa.selenium.devtools.v91.log.Log;
import org.openqa.selenium.devtools.v91.network.Network;
import org.openqa.selenium.devtools.v91.network.model.ConnectionType;
import org.openqa.selenium.devtools.v91.network.model.Headers;
import org.openqa.selenium.devtools.v91.performance.Performance;
import org.openqa.selenium.devtools.v91.performance.model.Metric;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class DevToolsTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(DevToolsTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Предоставление прав браузеру на выполнение операций
    @Test
    public void browserPermissionsTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Предоставление прав браузеру на выполнение операций
        // Browser.grantPermissions - предоставление прав браузеру
        // Список предоставляемых прав
        List<PermissionType> permissins = new ArrayList<>();
        permissins.add(PermissionType.AUDIOCAPTURE);
        permissins.add(PermissionType.VIDEOCAPTURE);
        permissins.add(PermissionType.GEOLOCATION);
        devTools.send(Browser.grantPermissions(permissins, Optional.empty(), Optional.empty()));

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Получение данных о браузере
    @Test
    public void browserVersionTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Получение данных о браузере
        // Browser.getVersion - получение данных о версии
        Browser.GetVersionResponse version = devTools.send(Browser.getVersion());

        // Вывод данных о браузере
        logger.info("Browser: " + version.getProduct());
        logger.info("User Agent: " + version.getUserAgent());
        logger.info("JS: " + version.getJsVersion());
        logger.info("Protocol: " + version.getProtocolVersion());

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        waitingForAPage(4);
    }

    // Установка параметров эмулируемого устройства
    @Test
    public void emulationSetDeviceMetricsTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Установка параметров эмулируемого устройства
        // Emulation.setDeviceMetricsOverride - установка параметров
        // Параметры эмулируемого устройства

        int width = 600;            // Ширина
        int height = 1000;          // Высота
        int deviceScaleFactor = 50; //
        boolean mobile = false;     // Мобильное устройство
        devTools.send(Emulation.setDeviceMetricsOverride(
                width, height, deviceScaleFactor, mobile,
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty())
        );

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        logger.info("Параметры экрана");
        logger.info("Ширина: " + chrome.executeScript("return document.documentElement.clientWidth"));
        logger.info("Высота: " + chrome.executeScript("return document.documentElement.clientHeight"));

        waitingForAPage(4);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Установка геолокации
    @Test
    public void emulationSetGeoLocationTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Установка геолокации
        // Emulation.setGeolocationOverride - установка геолокации
        // Параметры геолокации
        Optional<Number> latitude = Optional.of(50.52930);   // Широта
        Optional<Number> longitude = Optional.of(42.68620);  // Долгота
        Optional<Number> accuracy = Optional.of(10);         //Точность
        devTools.send(Emulation.setGeolocationOverride(latitude, longitude, accuracy));

        // Открыть страницу mycurrentlocation.net
        chrome.get("https://mycurrentlocation.net/");
        logger.info("Открыта страница mycurrentlocation.net - " + "https://mycurrentlocation.net/");

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    } // как переключится на подтверждения гео локации?

    // Вывод логов
    @Test
    public void logListenerTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Вывод логов
        // Log.enable - включение доступа к логам
        devTools.send(Log.enable());

        // Слушатель
        devTools.addListener(Log.entryAdded(),
                // Вывод логов
                logEntry -> {
                    logger.info("Log: " + logEntry.getText());
                    logger.info("Level: " + logEntry.getLevel());
                }
        );

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        // Ввести в строку поиска текст - Selenium
        WebElement input = driver.findElement(By.xpath("//input[@class=\"input__control input__input mini-suggest__input\"]"));
        input.sendKeys("Selenium");

        // Нажать на кнопку "Найти"
        String buttonXPath = "//button[@class=\"button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited\"]";
        WebElement button = driver.findElement(By.xpath(buttonXPath));
        button.click();

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Сбор метрик
    @Test
    public void performanceMetricsTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Сбор метрик
        // Performance.enable - включение сбора метрик
        devTools.send(Performance.enable(Optional.of(Performance.EnableTimeDomain.TIMETICKS)));

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        // Ввести в строку поиска текст - Selenium
        WebElement input = chrome.findElement(By.xpath("//input[@class=\"input__control input__input mini-suggest__input\"]"));
        input.sendKeys("Selenium");

        // Нажать на кнопку "Найти"
        WebElement button = chrome.findElement(By.xpath("//button[@class=\"button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited\"]"));
        button.click();

        waitingForAPage(5);

        // Получение метрик
        List<Metric> metrics = devTools.send(Performance.getMetrics());
        List<String> metricNames = metrics.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());

        // Performance.disable - выключение сбора метрик
        devTools.send(Performance.disable());

        // Список метрик для проверки
        List<String> metricsToCheck = Arrays.asList(
                "Timestamp",
                "Documents",
                "Frames",
                "JSEventListeners",
                "LayoutObjects",
                "MediaKeySessions",
                "Nodes",
                "Resources",
                "DomContentLoaded",
                "NavigationStart"
        );

        // Вывод метрик
        metricsToCheck.forEach(metric ->
                logger.info(metric + " is : " + metrics.get(metricNames.indexOf(metric)).getValue())
        );

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Перехват и модификация запросов
    @Test
    public void fetchRequestsTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Перехват запроса
        // Fetch.enable - включение перехвата запроса
        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        // Слушатель
        devTools.addListener(
                // Fetch.requestPaused - остановка запроса
                Fetch.requestPaused(),
                // Обработка запроса
                request -> {
                    // Новый URL запроса (подставляем в Fetch.continueRequest)
                    // Но нужно знать что подставлять иначе зафейлится
                    String newUrl = request.getRequest().getUrl().contains("yandex.ru")
                            ? request.getRequest().getUrl().replace("yandex.ru", "ya.ru")
                            : request.getRequest().getUrl();
                    // Параметры запроса
                    RequestId requestId = request.getRequestId();                            // ID запроса
                    Optional<String> url = Optional.of(request.getRequest().getUrl());       // URL запроса
                    Optional<String> method = Optional.of(request.getRequest().getMethod()); // Метод запроса
                    Optional<String> postData = request.getRequest().getPostData();          // Данные POST запроса
                    Optional<List<HeaderEntry>> headers = request.getResponseHeaders();      // Заголовки запроса
                    // Fetch.continueRequest - продолжение запроса
                    devTools.send(Fetch.continueRequest(requestId, url, method, postData, headers));
                }
        );

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        // Ввести в строку поиска текст - Selenium
        WebElement input = chrome.findElement(By.xpath("//input[@class=\"input__control input__input mini-suggest__input\"]"));
        input.sendKeys("Selenium");

        // Нажать на кнопку "Найти"
        WebElement button = chrome.findElement(By.xpath("//button[@class=\"button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited\"]"));
        button.click();

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    //Эмуляция интернет соединения
    @Test
    public void networkConnectEmulationTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Эмуляция интернет соединения
        // Network.enable - включение сетевых возможностей
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Network.emulateNetworkConditions - эмуляция интернет соединения
        // Параметры соединения
        Boolean offline = false;            // Оффлайн режим
        Number latency = 2;                 // Задержка
        Number downloadThroughput = 80000;  // Пропускная способность скачивания
        Number uploadThroughput = 200000;   // Пропускная способность загрузки
        Optional<ConnectionType> connectionType = Optional.of(ConnectionType.CELLULAR2G); // Тип соединения
        devTools.send(Network.emulateNetworkConditions(offline, latency, downloadThroughput, uploadThroughput, connectionType));

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        // Ввести в строку поиска текст - Selenium
        WebElement input = chrome.findElement(By.xpath("//input[@class=\"input__control input__input mini-suggest__input\"]"));
        input.sendKeys("Selenium");

        // Нажать на кнопку "Найти"
        WebElement button = chrome.findElement(By.xpath("//button[@class=\"button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited\"]"));
        button.click();

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    //Добавление заголовков в запросы
    @Test
    public void networkAddHeadersTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Добавление заголовков в запросы
        // Network.enable - включение сетевых возможностей
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Network.setExtraHTTPHeaders - отправка заголовков запроса
        // Заголовки
        Map<String, Object> headers = new HashMap<>();
        headers.put("headerName1", "headerValue1");
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        // Слушатель
        devTools.addListener(
                // Network.requestWillBeSent - перехват запроса
                Network.requestWillBeSent(),
                // Обработка запроса
                request -> {
                    if (request.getRequest().getHeaders().get("headerName1").equals("headerValue1"))
                        logger.info("URL: " + request.getRequest().getUrl() +
                                "\nHeaders: " + request.getRequest().getHeaders());
                }
        );

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Перехват HTTP запроса
    @Test
    public void networkCaptureHttpRequestTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Перехват HTTP запроса
        // Network.enable - включение сетевых возможностей
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Слушатель
        devTools.addListener(
                // Network.requestWillBeSent - перехват запроса
                Network.requestWillBeSent(),
                // Обработка запроса
                request -> {
                    if (request.getRequest().getUrl().contains("zen.yandex"))
                        logger.info(request.getRequest().getMethod() + " " + request.getRequest().getUrl());
                }
        );

        // Открыть страницу yandex.ru
        chrome.get("https://yandex.ru");
        logger.info("Открыта страница yandex.ru - " + "https://yandex.ru");

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
    }

    // Авторизация Basic Authentication
    @Test
    public void networkBasicAuthTest() {
        // Доступ к DevTools
        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();

        // Открытие сессии DevTools
        devTools.createSession();

        // Авторизация BasicAuth
        // Network.enable - включение сетевых возможностей
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Открыть страницу jigsaw.w3.org/HTTP/
        driver.get("https://jigsaw.w3.org/HTTP/");
        logger.info("Открыта страница jigsaw.w3.org/HTTP/ - " + "https://jigsaw.w3.org/HTTP/");

        // Отправка заголовков
        // Network.setExtraHTTPHeaders - отправка заголовков запроса
        Map<String, Object> headers = new HashMap<>(); // Заголовки
        final String USERNAME = "guest"; // Имя пользователя
        final String PASSWORD = "guest"; // Пароль пользователя
        byte[] encodedUserPass = new Base64().encode(String.format("%s:%s", USERNAME, PASSWORD).getBytes());
        String basicAuth = "Basic " + new String(encodedUserPass);
        headers.put("Authorization", basicAuth);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        // Нажать на ссылку "Basic Authentication test"
        WebElement link = driver.findElement(By.linkText("Basic Authentication test"));
        link.click();

        String loginSuccessMsg = driver.findElement(By.tagName("html")).getText();
        if (loginSuccessMsg.contains("Your browser made it!")) {
            logger.info("Успех!");
        } else {
            logger.info("Неудача!");
        }

        waitingForAPage(5);

        // Закрытие сессии DevTools
        devTools.close();
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
