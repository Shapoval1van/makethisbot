package com.makethisbot.bot.step;

import com.makethisbot.bot.entity.User;
import org.telegram.telegrambots.api.objects.Message;

public abstract class Step {

    protected Step nextStep;

    public Step getNotCompletedStep(User user) {
        if (isCurrentStepCompleted(user)) {
            return getNextStepIfNotCompleted(user);
        }
        return this;
    }

    public Step getNextStepIfNotCompleted(User user) {
        if (nextStep == null) {
            return this; //TODO we should detect conditional wen we has last step
        }
        return nextStep.getNotCompletedStep(user);
    }

    public Step getNextStep() {
        return nextStep;
    }

    public abstract boolean isCurrentStepCompleted(User user);

    public abstract boolean isDataValid(Message message);

    public abstract User updateUserData(User user, Message message);

    public abstract String getPromptMessage();

    public abstract String getUnSuccessMessage();
}
