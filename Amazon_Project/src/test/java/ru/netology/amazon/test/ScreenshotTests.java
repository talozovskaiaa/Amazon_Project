package ru.netology.amazon.test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import ru.netology.amazon.page.HomePage;
import ru.netology.amazon.page.MainPage;
import ru.netology.amazon.page.ShoppingCartPage;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreenshotTests {

    private static Page page;
    private HomePage homePage;
    private MainPage mainPage;

    @BeforeAll
    static void setupAll() {
        MainPage mainPageInstance = new MainPage();
        page = mainPageInstance.setUP(); // Сохраняем page в статическую переменную
    }

    @BeforeEach
    public void setup() {
        // Инициализируем страницы с уже созданным Page объектом
        homePage = new HomePage(page);
        // Если нужен MainPage, инициализируем его здесь
        mainPage = new MainPage();
        // Проверяем, что страница готова к использованию
        if (page == null) {
            throw new IllegalStateException("Page не был инициализирован в @BeforeAll");
        }
    }

    @Test
    public void takeScreenshotTest() {
        final String websiteLink = "https://www.amazon.com/";
        page.navigate(websiteLink);
        page.waitForLoadState();

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd_hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("./screenshots/screenshot_" + dateFormat.format(new Date()) + ".png")));


        assertEquals(websiteLink, page.url(),
                "URL страницы должен соответствовать ожидаемому");
    }

    @Test
    public void takeFullPageScreenshot() {
        final String websiteLink = "https://www.amazon.com/";
        page.navigate(websiteLink);
        page.waitForLoadState();

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd_hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("./screenshots/screenshot_" + dateFormat.format(new Date()) + ".png")).setFullPage(true));

        final String pageTitle = page.title();
        assertEquals(pageTitle, "Amazon.com. Spend less. Smile more.");
    }

    @Test
    public void takeElementScreenshot (){
        final String websiteLink = "https://www.amazon.com/";
        page.navigate(websiteLink);
        page.waitForLoadState();

        page.getByPlaceholder("Search Amazon")
                .screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("./screenshots/screenshot_element.png")));
    }
}
