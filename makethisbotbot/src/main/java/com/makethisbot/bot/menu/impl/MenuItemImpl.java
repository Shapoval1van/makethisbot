package com.makethisbot.bot.menu.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.util.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import javax.validation.constraints.NotNull;
import java.util.Locale;

import static com.makethisbot.bot.menu.util.Constants.MENU_BUTTON_TEXT_FORMAT;

public class MenuItemImpl implements MenuItem {

    protected Logger logger = LoggerFactory.getLogger(ContainerMenuItemImpl.class);

    protected MessagesUtil messagesUtil;
    protected String textKey;
    protected String id;
    protected String buttonTextKey;

    public MenuItemImpl(@NotNull String textKey,
                        @NotNull String id,
                        @NotNull String buttonTextKey,
                        @NotNull MessagesUtil messagesUtil) {
        this.textKey = textKey;
        this.id = id;
        this.buttonTextKey = buttonTextKey;
        this.messagesUtil = messagesUtil;
    }

    public MenuItemImpl(@NotNull String id,
                        @NotNull String buttonTextKey,
                        @NotNull MessagesUtil messagesUtil) {
        this.id = id;
        this.buttonTextKey = buttonTextKey;
        this.messagesUtil = messagesUtil;
    }

    @Override
    public SendMessage getSendMessage(User user) {
        return getSendMessage(new Locale(user.getLanguageCode()));
    }

    @Override
    public SendMessage getSendMessage(Locale locale) { //we get SendMessage with already localized text field
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messagesUtil.getMessageByKey(getTextKey(), locale));
        return sendMessage;
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(String.format(MENU_BUTTON_TEXT_FORMAT, getId(), getButtonTextKey()));
    }

    @Override
    public String getTextKey() { //default  message text same that button text
        return textKey == null ? getButtonTextKey() : textKey;
    }

    @Override
    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    @Override
    public void setMessagesUtil(MessagesUtil messagesUtil) {
        this.messagesUtil = messagesUtil;
    }

    @Override
    public String getButtonTextKey() {
        return buttonTextKey;
    }

    @Override
    public void setButtonTextKey(String buttonTextKey) {
        this.buttonTextKey = buttonTextKey;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
