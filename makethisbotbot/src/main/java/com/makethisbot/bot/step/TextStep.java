package com.makethisbot.bot.step;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Locale;

public abstract class TextStep extends Step {

    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        return new SendMessage(chatId, messagesUtil.getMessageByKey(getUnSuccessMessageKey(), locale))
                .setReplyMarkup(new ReplyKeyboardMarkup());
    }

    public SendMessage getPromptSendMessage(Long chatId, Locale locale) {
        return new SendMessage(chatId, messagesUtil.getMessageByKey(getPromptMessageKey(), locale))
                .setReplyMarkup(new ReplyKeyboardMarkup());
    }
}
