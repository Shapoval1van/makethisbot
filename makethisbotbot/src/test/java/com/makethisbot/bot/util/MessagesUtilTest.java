package com.makethisbot.bot.util;

import com.makethisbot.bot.menu.MenuItemsIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static com.makethisbot.bot.menu.util.Constants.BUTTON_TEXT_FORMAT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class MessagesUtilTest {

    @Spy
    MessagesUtil messagesUtil = new MessagesUtil();

    @Test
    public void getLocalizedButtonMenuText() throws Exception {
        String messageKey = "key";
        Locale locale = new Locale("ua");
        String message = "message";
        doReturn(message).when(messagesUtil).getMessageByKey(messageKey, locale);
        String localizedMessage = messagesUtil.getLocalizedButtonMenuText
                (String.format(BUTTON_TEXT_FORMAT, MenuItemsIds.FAQ_MENU_ITEM_ID.getId(), messageKey), locale);
        assertEquals((String.format(BUTTON_TEXT_FORMAT, MenuItemsIds.FAQ_MENU_ITEM_ID.getId(), message)), localizedMessage);
    }

    @Test
    public void isButtonMenuTextValid_shouldReturnTrue() throws Exception {
        assertFalse(messagesUtil.isButtonMenuTextValid(MenuItemsIds.FAQ_MENU_ITEM_ID.getId() + "hi bot!!!!!"));
    }
}