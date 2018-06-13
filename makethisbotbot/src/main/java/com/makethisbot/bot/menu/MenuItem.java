package com.makethisbot.bot.menu;

import com.makethisbot.bot.util.MessagesUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Locale;

public interface MenuItem {

    /**
     * @return get message that will send after click button
     */
    SendMessage getSendMessage(Locale locale);

    /**
     * @return {@link KeyboardButton} which wrap menu item
     */
    KeyboardButton getKeyboardButton();

    /**
     * @return buttonId with the help of we will find in menu tree which button was clicked on
     */
    String getId();

    void setId(String id);

    /**
     * @return button text
     */
    String getButtonTextKey();

    void setButtonTextKey(String buttonTextKey);

    /**
     * @return text that will be sending if button clicked by default return [buttonId]. ButtonText
     */
    String getTextKey();

    void setTextKey(String textKey);

    void setMessagesUtil(MessagesUtil messagesUtil);
}
