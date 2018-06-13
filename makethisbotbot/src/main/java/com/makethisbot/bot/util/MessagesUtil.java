package com.makethisbot.bot.util;

import com.makethisbot.bot.menu.MenuItemsIds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.makethisbot.bot.menu.util.Constants.MENU_BUTTON_TEXT_SEPARATOR;

@Component
public class MessagesUtil {

    @Value("${messages.path}")
    protected String messagesPath;

    public String getMessageByKey(String key, Locale locale) {
        if (locale.getLanguage().equals("ua")) {
            locale = new Locale("ru");
        }
        ResourceBundle messages = ResourceBundle.getBundle(messagesPath, locale);
        return messages.getString(key);
    }

    public String getLocalizedButtonMenuText(String buttonMenuText, Locale locale) {
        if (!isButtonMenuTextValid(buttonMenuText)) {
            return "Some error occurrence";
        }
        String[] splittedButtonMenuText = buttonMenuText.split(Pattern.quote(MENU_BUTTON_TEXT_SEPARATOR));
        return splittedButtonMenuText[0] + MENU_BUTTON_TEXT_SEPARATOR + getMessageByKey(splittedButtonMenuText[1], locale);
    }

    protected boolean isButtonMenuTextValid(String buttonMenuText) {
        String[] splittedButtonMenuText = buttonMenuText.split(Pattern.quote(MENU_BUTTON_TEXT_SEPARATOR));
        for (MenuItemsIds menuItemsIds : MenuItemsIds.values()) {
            if (menuItemsIds.getId().equals(splittedButtonMenuText[0])) {
                return true;
            }
        }
        return false;
    }
}
