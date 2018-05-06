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
    public boolean isDataValid(Message message) {
        String text = message.getText();
        return !StringUtils.isEmpty(text); //TODO add more complicated validation
    }

    @Override
    public User updateUserData(User user, Message message) {
        String name = message.getText();
        user.setEmail(name);
        return user;
    }


    @Override
    public String getPromptMessageKey() {
        return "enter.email.prompt";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "enter.email.unsuccess";
    }
}
