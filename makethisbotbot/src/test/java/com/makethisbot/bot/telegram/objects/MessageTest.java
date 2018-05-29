package com.makethisbot.bot.telegram.objects;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

public class MessageTest extends Message {

    private User user;

    private String text;

    private Long chatId;

    public void setFrom(User user) {
        this.user = user;
    }

    @Override
    public User getFrom() {
        return this.user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getChatId(){
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }
}
