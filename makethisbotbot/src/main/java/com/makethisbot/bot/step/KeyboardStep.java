package com.makethisbot.bot.step;

import com.makethisbot.bot.step.impl.EndStep;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Locale;

public abstract class KeyboardStep extends Step {

    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = getUnSuccessMessageKey();
        String text = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setReplyMarkup(((KeyboardStep) nextStep).getKB());
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage getPromptSendMessage(Long chatId, Locale locale) {
        if ((nextStep == null) && !(((Step)this) instanceof EndStep)) {
            logger.error("Wrong Configuration next step can be null only fot EndStep");
            return new SendMessage(chatId, "Sorry configuration problem");
        } // TODO if this will be end step we will got NPE, need fix it
        return getNextPromptSendMessage(chatId, locale);
    }

    public abstract ReplyKeyboardMarkup getKB();
}
