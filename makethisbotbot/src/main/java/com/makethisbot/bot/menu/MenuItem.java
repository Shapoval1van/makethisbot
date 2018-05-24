package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

public interface MenuItem {

    SendMessage getSendMessage();

    Integer getId();

    String getText();

    KeyboardButton getKeyboardButton();

    void addChildItem(MenuItem menuItem);
}
