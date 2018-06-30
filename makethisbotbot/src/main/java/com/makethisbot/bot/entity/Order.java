package com.makethisbot.bot.entity;


import lombok.Getter;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Order {

    @Getter
    @Setter
    private String describe;

    @Getter
    @Setter
    private OrderType type;

    @Getter
    @Setter
    private Date dueDate;

    @Getter
    @Setter
    private OrderStatus orderStatus;

    private URL blobFileURL;

    //TODO add link to specification
    public void setBlobFileURL(String url) {
        try {
            blobFileURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getBlobFileURL() {
        return blobFileURL.toString();
    }
}
