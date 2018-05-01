package com.makethisbot.bot.telegram.objects;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.User;

public class UserTest extends User{

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String languageCode;

}
