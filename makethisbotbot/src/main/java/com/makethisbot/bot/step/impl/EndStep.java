package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.step.TextStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Locale;

@Component("endStep")
public class EndStep extends TextStep {

    @Autowired
    MenuItem rootMenuItem;

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return false;
    }

    @Override
    public boolean isDataValid(Message message) {
        return false;
    }

    @Override
    public User updateUserData(User user, Message message) {
        return null;
    }

    @Override
    public String getPromptMessageKey() {
        return "";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "";
    }

    @Override
    public SendMessage getPromptSendMessage(Long chatId, Locale locale) {
        return rootMenuItem.getSendMessage(locale).setChatId(chatId); //link end step with menu whe all ste is completed instead prompt to next step we send root menu item
    }

}
