package ru.netology.amazon.page;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class RemoveAllServicesExample {

    private final Locator navArrowDown;
    private final Locator delete;
    private final Locator deleteApprove;

    /**
     * Конструктор для инициализации локаторов элементов на странице,
     * используемых при удалении всех сервисов/услуг.
     */
    public RemoveAllServicesExample(Page page) {
        this.navArrowDown = page.locator("button[data-action=\"a-stepper-decrement\"]\n");
        this.delete = page.locator("input[data-action='delete-active']");
        this.deleteApprove = page.locator("your-confirm-delete-button-selector");
    }

    /**
     * Метод для удаления всех товаров в корзине
     * Сейчас не работает, его надо поправить и довести до ума
     */
    public void removeAll() throws InterruptedException {
        Page page = navArrowDown.page();

        if (navArrowDown.first().isVisible()) {
            navArrowDown.first().click();

            while (navArrowDown.count() > 0) {
                navArrowDown.first().hover();
                navArrowDown.first().click();
//                deleteApprove.click();

                page.waitForSelector(navArrowDown.toString(),
                        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
            }
        } else if (navArrowDown.first().isHidden()) {
            System.out.println("Все товары удалены");
        }
    }
}