package com.makethisbot.bot.util;

import com.makethisbot.bot.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Locale;

@Component
public class UserUtil {

    public User getUserFromTelegramUpdate(Update update) {
        User user = new User();
        user.setId(update.getMessage().getFrom().getId());
        user.setTelegramLastName(update.getMessage().getFrom().getLastName());
        user.setTelegramFirsName(update.getMessage().getFrom().getFirstName());
        user.setTelegramUsername(update.getMessage().getFrom().getUserName());
        user.setLanguageCode(update.getMessage().getFrom().getLanguageCode());
        return user;
    }

    public int getUserIdFromUpdate(Update update) {
        return update.getMessage().getFrom().getId();
    }

    public Locale getLocalFromUser(User user) {
        return new Locale(user.getLanguageCode());
    }
}
