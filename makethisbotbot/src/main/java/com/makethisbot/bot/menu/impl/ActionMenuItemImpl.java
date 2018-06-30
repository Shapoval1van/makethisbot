package com.makethisbot.bot.menu.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.ActionMenuItem;
import com.makethisbot.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.validation.constraints.NotNull;

public abstract class ActionMenuItemImpl extends MenuItemImpl implements ActionMenuItem {

    @Autowired
    private UserRepository userRepository;

    public ActionMenuItemImpl(@NotNull String textKey,
                              @NotNull String id,
                              @NotNull String buttonTextKey) {
        super(textKey, id, buttonTextKey);
    }

    @Override
    public SendMessage getSendMessage(User user) {
        doAction(user);
        return super.getSendMessage(user);
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }
}
