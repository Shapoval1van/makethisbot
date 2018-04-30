package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

@Component("enterEmailStep")
public class EmailEnterStep extends Step {

    @Autowired
    @Qualifier("endStep")
    public void setStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return !StringUtils.isEmpty(user.getEmail());
    }

    @Override
    public void saveDataFromMessage(Message message) {

    }

    @Override
    protected String getPromptMessage() {
        return "Enter email please";
    }

    @Override
    protected String getUnSuccessMessage() {
        return "Please try again";
    }
}
