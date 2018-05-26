package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface MenuItem {

    /**
     * @return get message that will send after button click button
     */
    SendMessage getSendMessage();

    /**
     * @return buttonId with the help of we will find in menu tree which button was clicked on
     */
    String getId();

    /**
     *  @return button text
     */
    String getButtonText();

    /**
     * all menu item has self KeyboardButton
     *
     * @return
     */
    KeyboardButton getKeyboardButton();

    /**
     * add child menu item that will show after click on the current menu item
     *
     * @param childItem
     */
    void addChildItem(MenuItem childItem);

    /**
     *
     * @return backButton id if return empty string its mean that back button not allowed
    */
    String getBackButtonId();

    /**
     *
     *  @return text that will be send if button clicked by default return [buttonId]. ButtonText
     */
    String getText();

    List<MenuItem> getChildMenuItems();

    List<KeyboardRow> getKeyboardRowList();
}
