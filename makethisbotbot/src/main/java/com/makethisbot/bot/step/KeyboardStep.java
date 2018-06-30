package com.makethisbot.bot.step;

import com.makethisbot.bot.util.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public abstract class KeyboardStep extends Step {

    @Autowired
    protected MessagesUtil messagesUtil;

    public final static String FORMAT = "%d. %s";

    @Override
    public boolean isDataValid(Message message) {
        String messageText = message.getText();
        String[] splittedMessageText = messageText.split(Pattern.quote("."));
        if (splittedMessageText.length == 0) {
            return false;
        }
        int buttonIndex;
        try {
            buttonIndex = Integer.parseInt(splittedMessageText[0]);
        } catch (NumberFormatException e) {
            return false;
        }
        return buttonIndex <= getKeyboardTitleKey().size() && buttonIndex >= 0;
    }

    @Override
    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        SendMessage sendMessage = new SendMessage();
        String key = getUnSuccessMessageKey();
        String text = messagesUtil.getMessageByKey(key, locale);
        sendMessage.setReplyMarkup(getKeyboard(locale));
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
        sendMessage.setReplyMarkup(getKeyboard(locale));
        return sendMessage;
    }

    public abstract ReplyKeyboard getKeyboard(Locale locale);

    public abstract List<String> getKeyboardTitleKey();
}
