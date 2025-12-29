package ru.netology.amazon.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ShoppingCartPage {

    private final Page page;

    public static final String NAVIGATION_MENU = "#nav-hamburger-menu";
    public static final String ELECTRONICS_SECTION = "a.hmenu-item:has-text(\"Electronics\")";
    public static final String PHOTO_SUBSECTION = "div[data-menu-id=\"6\"] a.hmenu-item:has-text(\"Camera & Photo\")";
    public static final String RESULT_SUBSECTION = "h2.a-size-medium-plus:has-text(\"Results\")";
    public static final String ADD_TO_CART_FIRST_BUTTON = "#a-autoid-1";
    public static final String CART_BUTTON = "#nav-cart";
    public static final String SEARCH_FIELD = "#twotabsearchtextbox";
    public static final String SEARCH_BUTTON ="#nav-search-submit-button";
    public static final String ADD_TO_CART_BUTTON_FOR_MACBOOK = "#a-autoid-4-announce";
    public static final String ADD_TO_CART_BUTTON_FOR_PUPPETS = "#a-autoid-3-announce";
    public static final String INCREASE_THE_NUMBER = "button[aria-label=\"Increase quantity by one\"]";
    public static final String CONFIRMATION_OF_ITEM_ADDITION = "div.a-stepper-inner-container";
    public static final String SHOPPING_CART = "#sc-active-items-header";
    public static final String DECREASE_THE_NUMBER_IN_CART = "button[data-action=\"a-stepper-decrement\"]";
    public final static String FIRST_DELETE_BUTTON = "input[data-action=\"delete-active\"]";
    public final static String TEXT_AFTER_DELETION = "div[data-action='delete']";
    public final static String PROCEED_TO_CHECKOUT = "#sc-buy-box-ptc-button-announce";
    public final static String HEADER_SECURE_CHECKOUT = "#nav-checkout-title-header-text";
    public final static String ADD_A_NEW_DELIVERY_ADDRESS = "#add-new-address-desktop-sasp-tango-link";
    public final static String PHONE_NUMBER_FIELD = "#address-ui-widgets-enterAddressPhoneNumber";
    public final static String ADDRESS_FIELD = "#address-ui-widgets-enterAddressLine1";
    public final static String SUITE_NUMBER = "#address-ui-widgets-enterAddressLine2";
    public final static String USE_THIS_ADDRESS_BUTTON = "#checkout-primary-continue-button-id input";
    public Object deleteAnItemsFromTheCart;

    public ShoppingCartPage(Page page) {
        this.page = page;
    }

    /**
     * Метод для перехода в любой раздел бокового меню
     */
    public void navigateToSection(String sectionSelector, String subsectionSelector, String headerSelector) {
        page.locator(NAVIGATION_MENU).click();
        page.locator(sectionSelector).first().click();
        Locator photoItem = page.locator(subsectionSelector).first();
        photoItem.scrollIntoViewIfNeeded();
        photoItem.click(new Locator.ClickOptions()
                .setForce(true)
                .setTimeout(10000));
        page.locator(headerSelector).first().waitFor();
    }

    /**
     * Добавление товара в корзину и переход в корзину
     */
    public void addToCart(String locatorByAddToCartButton) {
        page.locator(locatorByAddToCartButton)
//                .first()
                .click(new Locator.ClickOptions().setTimeout(10000));

//        waitForAddToCartConfirmation();

//        page.locator(INCREASE_THE_NUMBER)
//                .first()
//                .click(new Locator.ClickOptions().setTimeout(5000));

        page.evaluate("window.scrollTo(0, 0)");
        page.waitForTimeout(1000);

        navigateToCart();
    }

    /**
     * Метод для поиска товара через поисковую строку
     */
    public void searchItem(String items) {
        page.locator(SEARCH_FIELD).waitFor(new Locator.WaitForOptions().setTimeout(10000));
        page.locator(SEARCH_FIELD).click();
        page.locator(SEARCH_FIELD).fill(items);
        page.locator(SEARCH_BUTTON).click();
        page.locator(RESULT_SUBSECTION).first().waitFor();
    }

    public void waitForAddToCartConfirmation () {
        page.locator(CONFIRMATION_OF_ITEM_ADDITION);
    }

    /**
     * Метод для перехода в корзину
     */
    public void navigateToCart() {
        page.locator(CART_BUTTON).waitFor(new Locator.WaitForOptions().setTimeout(10000));
        page.locator(CART_BUTTON).click();
        page.locator(SHOPPING_CART).waitFor();
    }

    /**
     * Метод для уменьшения количества товара в корзине на один
     */
    public void decreaseTheNumberInCart() {
        page.locator(DECREASE_THE_NUMBER_IN_CART)
                .first()
                .click(new Locator.ClickOptions().setTimeout(5000));
    }

    /**
     * Метод для удаления товара из корзины
     */
    public void deleteAnItemsFromTheCart(String locator_by_delete_button, String text_after_deletion) {
        page.click(locator_by_delete_button);
        page.waitForSelector(text_after_deletion);
    }

    /**
     * Метод нажатия на кнопку Продолжить оформление заказа
     */
    public void proceedToCheckout() {
        page.locator(PROCEED_TO_CHECKOUT)
                .click(new Locator.ClickOptions()
                        .setForce(true)
                        .setTimeout(10000));
        page.locator(HEADER_SECURE_CHECKOUT).waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }

    /**
     * Метод для добавления нового адреса при оформлении заказа
     */
    public void addANewAddress(String phone_number, String address, String suite_number) {
        page.locator(ADD_A_NEW_DELIVERY_ADDRESS).click();
        page.locator(PHONE_NUMBER_FIELD).fill(phone_number);
        page.locator(ADDRESS_FIELD).fill(address);
        page.locator(SUITE_NUMBER).fill(suite_number);
        Locator element = page.locator(USE_THIS_ADDRESS_BUTTON).nth(1);
        element.scrollIntoViewIfNeeded();
        element.waitFor();
        element.click();
    }

    /**
     * Локатор для счетчика товаров в корзине
     */
    public int getCartItemsCount() {
        return Integer.parseInt(page.locator("#nav-cart-count").innerText());
    }
}
