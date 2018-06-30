package com.makethisbot.bot.menu.util;

import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.impl.ContainerMenuItemImpl;
import com.makethisbot.bot.menu.impl.MenuItemImpl;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.util.MessagesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_QUESTION1_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.ROOT_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MenuUtilTest {

    @Mock
    public MessagesUtil messagesUtil;

    public LayoutManager rootMenuItemLayout() {
        return new LayoutManager()
                .addRow(1);
    }


    public LayoutManager faqMenuItemLayout() {
        return new LayoutManager()
                .addRow(1);
    }

    @InjectMocks
    MenuItem faqMenuItem1 = new MenuItemImpl("menu.faq.question1",
            FAQ_MENU_QUESTION1_ITEM_ID.getId(),
            "menu.faq.question1.button");

    @InjectMocks
    MenuItem faqMenuItemContainer = new ContainerMenuItemImpl("menu.faq",
            FAQ_MENU_ITEM_ID.getId(),
            "menu.faq.button",
            TO_ROOT_BACK_BUTTON_ID.getId(),
            Arrays.asList(faqMenuItem1),
            faqMenuItemLayout());

    @InjectMocks
    MenuItem rootMenuItem = new ContainerMenuItemImpl("menu.root",
            ROOT_MENU_ITEM_ID.getId(),
            "menu.root.button",
            "",
            Arrays.asList(faqMenuItemContainer),
            rootMenuItemLayout());

    @Test
    public void addBackButtons() throws Exception {
        Map<String, MenuItem> backButtonsMap = new HashMap<>();
        MenuUtil menuUtil = new MenuUtil();
        menuUtil.addBackButtons(rootMenuItem, backButtonsMap);
        assertEquals(backButtonsMap.size(), 1);
        assertEquals(((ContainerMenuItemImpl)faqMenuItemContainer).getKeyboardRowList().size(), 2);
        assertEquals(((ContainerMenuItemImpl)faqMenuItemContainer).getChildMenuItems().size(),1);
    }

}