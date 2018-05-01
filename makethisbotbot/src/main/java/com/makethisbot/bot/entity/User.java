package com.makethisbot.bot.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "user")
public class User {

    @Getter
    @Setter
    @Id
    @Column(name = "_id")
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String contactPhoneNumber;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Order order;

    @Getter
    @Setter
    private String telegramFirsName;

    @Getter
    @Setter
    private String telegramLastName;

    @Getter
    @Setter
    private String telegramUsername;

    @Getter
    @Setter
    private String languageCode;
}
