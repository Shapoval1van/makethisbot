package com.makethisbot.bot.telegram.objects;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

public class MessageTest extends Message {

    private User user;

    public void setFrom(User user) {
        this.user = user;
    }

    @Override
    public User getFrom() {
        return this.user;
    }
}
