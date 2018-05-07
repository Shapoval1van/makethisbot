package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("phoneEnterStep")
public class PhoneEnterStep extends Step {

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile(
                    "^((38|\\+38)(-)??)?(\\(?(044|093|067|073|098|050|068|063|097|096|095|066)\\)?)(-)??\\d{3}(-)??\\d{2}(-)??\\d{2}$",
                    Pattern.CASE_INSENSITIVE);

    @Autowired
    @Qualifier("orderDescribeEnterStep")
    public void setStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return !StringUtils.isEmpty(user.getPhoneNumber());
    }

    @Override
    public boolean isDataValid(Message message) {
        String text = message.getText();
        if(StringUtils.isEmpty(text)) {
            return false;
        }
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(text);
        return matcher.find();
    }

    @Override
    public User updateUserData(User user, Message message) {
        String name = message.getText();
        user.setPhoneNumber(name);
        return user;
    }

    @Override
    public String getPromptMessageKey() {
        return "enter.phone.prompt";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "enter.phone.unsuccess";
    }
}
