package com.makethisbot.bot.menu.item;

import com.makethisbot.bot.menu.KeyboardMenuItem;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_ITEM_ID;

@Component("faqMenuItem")
public class FAQMenuItem extends KeyboardMenuItem {



    public void init(){
        System.out.println("test");
    }

    @Override
    public Integer getId() {
        return FAQ_MENU_ITEM_ID;
    }

    @Override
    public String getText() {
        return "test FAQ";
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(getId() + getText());
    }

}
