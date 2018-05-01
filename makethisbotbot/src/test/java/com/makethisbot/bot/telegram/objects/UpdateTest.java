package com.makethisbot.bot.telegram.objects;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class UpdateTest extends Update {

    private Message message;

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return this.message;
    }
}
