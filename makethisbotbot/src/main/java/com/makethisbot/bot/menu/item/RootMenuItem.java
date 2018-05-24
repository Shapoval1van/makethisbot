package com.makethisbot.bot.menu.item;

import com.makethisbot.bot.menu.KeyboardMenuItem;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.layout.LayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import javax.annotation.PostConstruct;

import java.util.ArrayList;

import static com.makethisbot.bot.menu.MenuItemsIds.ROOT_MENU_ITEM_ID;

@Component
public class RootMenuItem extends KeyboardMenuItem {

    @Autowired
    MenuItem faqMenuItem;

    @PostConstruct
    protected void init() {
        childMenuItems = new ArrayList<>();
        childMenuItems.add(faqMenuItem);
        super.init();
    }

    @Autowired
    @Qualifier("rootMenuItemLayout")
    public void setLayout(LayoutManager layout) {
        this.layout = layout;
    }

    @Override
    public Integer getId() {
        return ROOT_MENU_ITEM_ID;
    }

    @Override
    public String getText() {
        return "root";
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(getId() + getText());
    }
}
