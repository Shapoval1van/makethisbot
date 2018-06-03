package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Locale;

public interface MenuItem {

    /**
     * @return get message that will send after click button
     */
    SendMessage getSendMessage(Locale locale);

    /**
     * @return buttonId with the help of we will find in menu tree which button was clicked on
     */
    String getId();

    /**
     * @return button text
     */
    String getButtonTextKey();

    /**
     * @return list of {@link MenuItem} witch contains button that will be show after click on current one
     */
    List<MenuItem> getChildMenuItems();

    /**
     *
     * @return {@link KeyboardButton} which wrap menu item
     */
    KeyboardButton getKeyboardButton();

    /**
     *
     * @return list of {@link KeyboardRow} which created based on {@link com.makethisbot.bot.menu.layout.LayoutManager} and
     * {@link #getChildMenuItems()} when {@link MenuItem} will be initialized
     */
    List<KeyboardRow> getKeyboardRowList();

    /**
     * add child menu item that will show after click on the current menu item
     *
     * @param childItem
     */
    void addChildItem(MenuItem childItem);

    /**
     * @return backButton id if return empty string its mean that back button not allowed
     */
    String getBackButtonId();

    /**
     * @return text that will be send if button clicked by default return [buttonId]. ButtonText
     */

    /**
     *
     * @return text that will sanded when button will be clicked
     */
    String getTextKey();
}
