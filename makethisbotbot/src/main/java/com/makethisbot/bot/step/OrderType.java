package com.makethisbot.bot.step;

public enum OrderType {

    BUSINESS(1, "enter.order.type.business"),
    GAME(2, "enter.order.type.game"),
    FUN(3, "enter.order.type.fun"),
    ORGANIZER(4, "enter.order.type.organizer"),
    DEFAULT(5, "enter.order.type.default");

    private String key;
    private Integer id;

    OrderType(int id, String key) {
        this.key = key;
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public Integer getId() {
        return id;
    }
}
