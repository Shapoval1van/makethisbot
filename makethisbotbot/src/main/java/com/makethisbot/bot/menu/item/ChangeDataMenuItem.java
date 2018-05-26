package com.makethisbot.bot.menu.item;

import com.makethisbot.bot.menu.KeyboardMenuItem;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID;

@Component("changeDataMenuItem")
public class ChangeDataMenuItem extends KeyboardMenuItem {

    @Override
    public String getId() {
        return CHANGE_DATA_MENU_ITEM_ID;
    }

    @Override
    public String getButtonText() {
        return "Change contact data";
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