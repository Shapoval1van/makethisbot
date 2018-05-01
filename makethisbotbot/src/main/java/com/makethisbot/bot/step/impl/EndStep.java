package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

@Component("endStep")
public class EndStep extends Step {

    @Override
    public Step getNotCompletedStep(User user) {
        return null;
    }

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return false;
    }

    @Override
    public boolean isDataValid(Message message) {
        return false;
    }

    @Override
    public User updateUserData(User user, Message message) {
        return null;
    }

    @Override
    public String getPromptMessage() {
        return null;
    }

    @Override
    public String getUnSuccessMessage() {
        return null;
    }

}
