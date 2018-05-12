package com.makethisbot.bot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MessagesUtil {


    @Value("${messages.path}")
    protected String messagesPath;

    public String getMessageByKey(String key, Locale locale) {
        if (locale.getLanguage() == "ua") {
            locale = new Locale("ru");
        }
        ResourceBundle messages = ResourceBundle.getBundle(messagesPath, locale);
        return messages.getString(key);
    }
}
