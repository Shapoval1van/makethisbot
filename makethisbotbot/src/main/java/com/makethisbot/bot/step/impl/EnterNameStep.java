package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

@Component(value = "enterNameStep")
public class EnterNameStep extends Step {


    @Autowired
    @Qualifier("enterEmailStep")
    public void setStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return !StringUtils.isEmpty(user.getName());
    }

    @Override
    public void saveDataFromMessage(Message message) {
//        Use
//        userRepository.
    }

    @Override
    protected String getPromptMessage() {
        return "Please enter your name";
    }

    @Override
    protected String getUnSuccessMessage() {
        return "It's look like a joke. Try again";
    }
}
