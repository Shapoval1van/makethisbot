package com.makethisbot.bot.conditional;

public interface ConditionalForTheNextStep {

    String getPromptMassage();

    boolean isDataAddedSuccessful();//put here validation logic

    String getMassageWhenDataNotValid();//put here mesassage that wil show when data not valid
}
