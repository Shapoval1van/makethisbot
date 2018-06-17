package com.makethisbot.bot.telegram.objects;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;

public class MessageTest extends Message {

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Long chatId;

    @Getter
    @Setter
    private List<MessageEntity> entities;

    public void setFrom(User user) {
        this.user = user;
    }

    @Override
    public User getFrom() {
        return this.user;
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }
}
