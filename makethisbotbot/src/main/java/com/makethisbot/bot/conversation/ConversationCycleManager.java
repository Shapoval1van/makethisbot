package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import com.makethisbot.bot.util.MessagesUtil;
import com.makethisbot.bot.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

@Component
public class ConversationCycleManager {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    @Qualifier("nameEnterStep")
    private Step step;

    @Autowired
    private MessagesUtil messagesUtil;

    @Autowired
    private UserUtil userUtil;

    public String processMessage(Message message, User user) {
        Step notCompletedStep = step.getNotCompletedStep(user);
        if (notCompletedStep instanceof EndStep) {
            return "Пока все нужно будет выдть кастомную клаву";
        }
        if (!notCompletedStep.isDataValid(message)) {
            String key = notCompletedStep.getUnSuccessMessageKey();
            return messagesUtil.getMessageByKey(key, userUtil.getLocalFromUser(user));
        }
        notCompletedStep.updateUserData(user, message);
        saveDataFromMessage(user);
        String key = notCompletedStep.getNextStep().getPromptMessageKey();
        return messagesUtil.getMessageByKey(key, userUtil.getLocalFromUser(user));
    }

    private void saveDataFromMessage(User user) {
        userRepository.save(user);
    }

}
