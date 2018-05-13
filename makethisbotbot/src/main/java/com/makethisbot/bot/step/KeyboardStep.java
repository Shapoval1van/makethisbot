package com.makethisbot.bot.step;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Locale;

public abstract class KeyboardStep extends Step {

    @Override
    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = getUnSuccessMessageKey();
        String text = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setReplyMarkup(((KeyboardStep) nextStep).getKeyboard());
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    @Override
    public SendMessage getPromptSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = getPromptMessageKey();
        String promptText = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setChatId(chatId);
        sendMessage.setText(promptText);
        sendMessage.setReplyMarkup(getKeyboard());
        return sendMessage;
    }

    public abstract ReplyKeyboard getKeyboard();
}
