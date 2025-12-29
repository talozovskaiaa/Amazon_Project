package ru.netology.amazon.test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import ru.netology.amazon.page.HomePage;
import ru.netology.amazon.page.MainPage;
import ru.netology.amazon.page.RemoveAllServicesExample;
import ru.netology.amazon.page.ShoppingCartPage;

import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.netology.amazon.page.ShoppingCartPage.*;

public class RemovalAfterTest {

    private static MainPage mainPage;
    private HomePage homePage;
    private ShoppingCartPage shoppingCartPage;
    private RemoveAllServicesExample removeAllServicesExample;
    private Page page;

    private static final Dotenv dotenv = Dotenv.load();
    private static final String login = dotenv.get("USER_EMAIL");
    private static final String password = dotenv.get("USER_PASSWORD");
    private static final Path AUTH_FILE_PATH = Paths.get("auth.json");

    @BeforeAll
    static void setupAll() {
        mainPage = new MainPage();

        Page page = mainPage.setUP();
        HomePage homePage = new HomePage(page);
        homePage.loginWithValidUser(login, password);

        page.context().storageState(new BrowserContext.StorageStateOptions().setPath(AUTH_FILE_PATH));
    }

    @BeforeEach
    @ResourceLock(Resources.SYSTEM_PROPERTIES)
    public void setup() {
        page = mainPage.setUPWithStorageState("auth.json");
        homePage = new HomePage(page);
        shoppingCartPage = new ShoppingCartPage(page);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        removeAllServicesExample = new RemoveAllServicesExample(page);
        removeAllServicesExample.removeAll();
            mainPage.tearDown();
    }

    @AfterAll
    static void tearDownAll() {
        try {
            java.nio.file.Files.deleteIfExists(AUTH_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Не удалось удалить файл auth.json: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Поиск 'Puppets' и добавление в корзину")
    @Severity(SeverityLevel.BLOCKER)
    void searchWithSearchField(){
        shoppingCartPage.searchItem("Puppets");
        shoppingCartPage.addToCart(
                ADD_TO_CART_BUTTON_FOR_PUPPETS
        );
    }
}
