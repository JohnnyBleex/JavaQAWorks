import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class PdfTest {
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(PdfTest.class);

    String env = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер запущен!");
    }

    // Получение текста файла PDF с помощью библиотеки PdfBox
    @Test
    public void pdfTextPdfBoxTest() {
        // Открыть страницу с PDF
        String pdfURL = "http://www.pdf995.com/samples/pdf.pdf";
        driver.get(pdfURL);

        try {
            // Открытие файла PDF
            PDDocument pdf = PDDocument.load(new BufferedInputStream(new URL(pdfURL).openStream()));

            // Извлечение текста из документа PDF
            String text = new PDFTextStripper().getText(pdf);
            logger.info(text);

            // Закрытие файла PDF
            pdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Получение текста файла PDF с помощью библиотеки iText
    @Test
    public void pdfTextITextPdfTest() {
        // Открыть страницу с PDF
        String pdfURL = "http://www.pdf995.com/samples/pdf.pdf";
        driver.get(pdfURL);

        try {
            // Открытие файла PDF
            PdfReader pdf = new PdfReader(pdfURL);

            // Извлечение текста из документа PDF
            // Нумерация страниц в PDF начинается с 1
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            for (int i = 1; i <= pdf.getNumberOfPages(); i++) {
                String text = PdfTextExtractor.getTextFromPage(pdf, i, strategy);
                logger.info(text);
            }
        } catch (IOException e) {
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
}
