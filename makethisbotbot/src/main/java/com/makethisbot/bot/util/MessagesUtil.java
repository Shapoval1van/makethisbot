package com.makethisbot.bot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public class MessagesUtil {


    @Value("${messages.path}")
    protected String messagesPath;

    private Locale defaultLocale = new Locale("en");

    public String getMessageByKey(String key, Locale locale) {
        ResourceBundle messages = null;
        try {
            messages = ResourceBundle.getBundle(messagesPath, locale);
        } catch (MissingResourceException e) {
            messages = ResourceBundle.getBundle(messagesPath, defaultLocale);
        }
        return messages.getString(key);
    }
}
