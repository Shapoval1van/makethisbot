package com.makethisbot.bot.entity;

public enum OrderStatus {

    QUEUE("status.queue"),
    ASSESSMENT("status.assessment"),
    CUSTOMER_REVIEW("status.customer.review"),
    DONE("status.done"),
    CANCELED("status.canceled");

    private String key;

    OrderStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
