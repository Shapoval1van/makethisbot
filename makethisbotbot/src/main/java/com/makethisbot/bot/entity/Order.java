package com.makethisbot.bot.entity;


import lombok.Getter;
import lombok.Setter;
import org.glassfish.grizzly.http.util.TimeStamp;

import java.net.MalformedURLException;
import java.net.URL;

public class Order {

    @Getter
    @Setter
    private String describe;

    @Getter
    @Setter
    private TimeStamp dueDate;

    private URL blobFileURL;

    public void setBlobFileURL(String url) {
        try {
            blobFileURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();//TODO add log
        }
    }

    public String getBlobFileURL() {
        return blobFileURL.toString();
    }
}
