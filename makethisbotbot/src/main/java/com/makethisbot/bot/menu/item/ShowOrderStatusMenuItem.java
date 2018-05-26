package com.makethisbot.bot.menu.item;

import com.makethisbot.bot.menu.KeyboardMenuItem;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.SHOW_ORDER_STATUS_MENU_ITEM_ID;

@Component("showOrderStatusMenuItem")
public class ShowOrderStatusMenuItem extends KeyboardMenuItem {
    @Override
    public String getId() {
        return SHOW_ORDER_STATUS_MENU_ITEM_ID;
    }

    @Override
    public String getButtonText() {
        return "Show current order status";
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(String.format(format, getId(), getButtonText()));
    }

    @Override
    public String getBackButtonId() {
        return TO_ROOT_BACK_BUTTON_ID;
    }
}
