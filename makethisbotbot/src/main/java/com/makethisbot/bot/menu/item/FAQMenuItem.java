package com.makethisbot.bot.menu.item;

import com.makethisbot.bot.menu.KeyboardMenuItem;
import com.makethisbot.bot.menu.layout.LayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.TO_FAQ_BACK_BUTTON_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;

@Component("faqMenuItem")
public class FAQMenuItem extends KeyboardMenuItem {


    @PostConstruct
    protected void init() {
        addChildItem(new KeyboardMenuItem() {
            @Override
            public String getId() {
                return ":game_die:";
            }

            @Override
            public String getButtonText() {
                return "Whay I do this";
            }

            @Override
            public String getBackButtonId() {
                return TO_FAQ_BACK_BUTTON_ID;
            }

            @Override
            public String getText() {
                return "yf [eq";
            }
        });
        super.init();
    }

    @Autowired
    @Qualifier("faqMenuItemLayout")
    public void setLayout(LayoutManager layout) {
        this.layout = layout;
    }

    @Override
    public String getId() {
        return FAQ_MENU_ITEM_ID;
    }

    @Override
    public String getButtonText() {
        return "FAQ";
    }

    @Override
    public String getBackButtonId() {
        return TO_ROOT_BACK_BUTTON_ID;
    }

}
