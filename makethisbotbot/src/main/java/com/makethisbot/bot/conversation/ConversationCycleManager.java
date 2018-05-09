package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import com.makethisbot.bot.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Locale;

@Component
public class ConversationCycleManager {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    @Qualifier("nameEnterStep")
    private Step step;

    @Autowired
    private UserUtil userUtil;

    public SendMessage processMessage(Message message, User user) {
        Step notCompletedStep = step.getNotCompletedStep(user);
        if (notCompletedStep instanceof EndStep) {
            return new SendMessage(message.getChatId(), "Пока все нужно будет выдть кастомную клаву");
        }
        Locale  locale = userUtil.getLocalFromUser(user);
        if (!notCompletedStep.isDataValid(message)) {
            return notCompletedStep.getUnsuccessSendMessage(message.getChatId(), locale);
        }
        notCompletedStep.updateUserData(user, message);
        saveDataFromMessage(user);
        return notCompletedStep.getPromptSendMessage(message.getChatId(), locale);
    }

    private void saveDataFromMessage(User user) {
        userRepository.save(user);
    }

}
