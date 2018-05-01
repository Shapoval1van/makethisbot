package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

@Component
public class ConversationCycleManager {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    @Qualifier("enterNameStep")
    private Step step;

    public String processMessage(Message message, User user) {
        Step notCompletedStep = step.getNotCompletedStep(user);
        if (notCompletedStep instanceof EndStep) {
            return "Пока все нужно будет выдть кастомную клаву";
        }
        if (!notCompletedStep.isDataValid(message)) {
            return notCompletedStep.getUnSuccessMessage();
        }
        notCompletedStep.updateUserData(user, message);
        saveDataFromMessage(user);
        return notCompletedStep.getNextStep().getPromptMessage();
    }

    private void saveDataFromMessage(User user) {
        userRepository.save(user);
    }

//    private String getTextForPromptMessage(Step step) {
////        Step nextStep = step.getNextStep();
////        if(nextStep != null) {
////
////        }
//        return "";
//    }
}
