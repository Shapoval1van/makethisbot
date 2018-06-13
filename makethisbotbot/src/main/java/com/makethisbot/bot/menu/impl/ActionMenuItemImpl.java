package com.makethisbot.bot.menu.impl;

import com.makethisbot.bot.menu.ActionMenuItem;
import com.makethisbot.bot.util.MessagesUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.validation.constraints.NotNull;
import java.util.Locale;

public class ActionMenuItemImpl extends MenuItemImpl implements ActionMenuItem {

    public ActionMenuItemImpl(@NotNull String textKey,
                              @NotNull String id,
                              @NotNull String buttonTextKey,
                              @NotNull MessagesUtil messagesUtil) {
        super(textKey, id, buttonTextKey, messagesUtil);
    }

    @Override
    public SendMessage getSendMessage(Locale locale) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messagesUtil.getMessageByKey(getTextKey(), locale));
        doAction();
        return sendMessage;
    }

    @Override
    public void doAction() {

    }
}
