package com.makethisbot.bot;

import com.makethisbot.bot.conversation.ConversationCycleManager;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UpdateHandler {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private ConversationCycleManager conversationCycleManager;

    public SendMessage processUpdate(Update update) {
        Message message = update.getMessage();
//        List<org.telegram.telegrambots.api.objects.User> newMembers = new ArrayList<>(Arrays.asList(new org.telegram.telegrambots.api.objects.User()));
        List<org.telegram.telegrambots.api.objects.User> newMembers = message.getNewChatMembers();

        if (newMembers != null && !newMembers.isEmpty()) {
            User user = getUserFromTelegramUpdate(update);
            userRepository.save(user);
            return new SendMessage(update.getMessage().getChatId(), "Hi new user maybe you want have the best bot in the world?????");//TODO move to property file
        } else if (message != null && message.hasText()) {
            Integer userId = getUserIdFromUpdate(update);
            User user = userRepository.findOne(userId);
            Step step = conversationCycleManager.determinateStepForUser(user);
            return new SendMessage(update.getMessage().getChatId(), "ewr");
        }
        return new SendMessage(update.getMessage().getChatId(), "ewr");
    }

    private User getUserFromTelegramUpdate(Update update) {
        User user = new User();
        user.set_id(update.getMessage().getFrom().getId());
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
