package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.TextStep;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("emailEnterStep")
public class EmailEnterStep extends TextStep {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
        return matcher.find();
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
