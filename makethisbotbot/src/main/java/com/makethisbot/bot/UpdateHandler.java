package com.makethisbot.bot;

import com.makethisbot.bot.conversation.ConversationCycleManager;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Component
public class UpdateHandler {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private ConversationCycleManager conversationCycleManager;

    public SendMessage processUpdate(Update update) {
        Message message = update.getMessage();
        Integer userId = getUserIdFromUpdate(update);
        User user = userRepository.findOne(userId);
        if (user == null) {
            user = getUserFromTelegramUpdate(update);
            userRepository.save(user);
            return new SendMessage(update.getMessage().getChatId(), "Hi new user, maybe you want have the best bot in the world?????"); //TODO move to property file
        } else if (message != null && message.hasText()) {
            String responseText = conversationCycleManager.processMessage(message, user);
            return new SendMessage(message.getChatId(), responseText);
        }
        return new SendMessage(message.getChatId(), "");
    }

    protected User getUserFromTelegramUpdate(Update update) {
        User user = new User();
        user.setId(update.getMessage().getFrom().getId());
        user.setTelegramLastName(update.getMessage().getFrom().getLastName());
        user.setTelegramFirsName(update.getMessage().getFrom().getFirstName());
        user.setTelegramUsername(update.getMessage().getFrom().getUserName());
        user.setLanguageCode(update.getMessage().getFrom().getLanguageCode());
        return user;
    }

    private int getUserIdFromUpdate(Update update) {
        return update.getMessage().getFrom().getId();
    }
}
