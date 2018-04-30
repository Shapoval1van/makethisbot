package com.makethisbot.bot.step;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;

public abstract class Step {

    @Autowired
    protected UserRepository userRepository;

    protected Step nextStep;

    public Step getNextStep() {
        return nextStep;
    }

    public Step getNotCompletedStep(User user) {
        if(isCurrentStepCompleted(user)) {
            return getNextStepIfNotCompleted(user);
        }
        return this;
    }

    public Step getNextStepIfNotCompleted(User user) {
        if(nextStep == null){
            return this;//TODO we should detect conditional wen we has last step
        }
        return nextStep.getNotCompletedStep(user);
    }

    public abstract boolean isCurrentStepCompleted(User user);

    public abstract void saveDataFromMessage(Message message);

    protected abstract String getPromptMessage();

    protected abstract String getUnSuccessMessage();
}
