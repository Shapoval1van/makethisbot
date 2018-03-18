package com.makethisbot.bot.conditional.impl;

import com.makethisbot.bot.conditional.ConditionalForTheNextStep;
import org.springframework.stereotype.Component;

@Component(value = "nameEnterCondition")
public class NameEnterConditional implements ConditionalForTheNextStep {

    @Override
    public String getPromptMassage() {
        return "Please enter your name";
    }

    @Override
    public boolean isDataAddedSuccessful() {
        return false;
    }

    @Override
    public String getMassageWhenDataNotValid() {
        return "It's look like a joke. Try again";
    }
}
