package com.makethisbot.bot.telegram.objects;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.MessageEntity;

public class MessageEntityTest extends MessageEntity {

    public MessageEntityTest(String type, Integer offset, Integer length) {
        this.type = type;
        this.offset = offset;
        this.length = length;
    }

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Integer offset;

    @Getter
    @Setter
    private Integer length;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private UserTest user;

    @Getter
    @Setter
    private String text;
}
