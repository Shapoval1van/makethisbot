package com.makethisbot.bot.step;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.util.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Locale;

public abstract class Step {

    protected Step nextStep;

    @Autowired
    protected MessagesUtil messagesUtil;

    protected Logger logger = LoggerFactory.getLogger(Step.class);

    protected SendMessage getNextPromptSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = nextStep.getPromptMessageKey();
        String promptText = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setChatId(chatId);
        sendMessage.setText(promptText);
        if (nextStep instanceof KeyboardStep) {
            sendMessage.setReplyMarkup(((KeyboardStep) nextStep).getKB());
        }
        return sendMessage;
    }

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

    public abstract SendMessage getUnsuccessSendMessage(Long chatId, Locale locale);

    public abstract SendMessage getPromptSendMessage(Long chatId, Locale locale);

    public abstract boolean isCurrentStepCompleted(User user);

    public abstract boolean isDataValid(Message message);

    public abstract User updateUserData(User user, Message message);

    public abstract String getPromptMessageKey();

    public abstract String getUnSuccessMessageKey();
}
