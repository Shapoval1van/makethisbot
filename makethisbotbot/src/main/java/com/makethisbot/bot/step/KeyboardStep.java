package com.makethisbot.bot.step;

import com.makethisbot.bot.step.impl.EndStep;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Locale;

public abstract class KeyboardStep extends Step {

    @Override
    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = getUnSuccessMessageKey();
        String text = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setReplyMarkup(((KeyboardStep) nextStep).getKB());
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
        sendMessage.setReplyMarkup(getKB());
        return sendMessage;
    }

    public abstract ReplyKeyboard getKB();
}
