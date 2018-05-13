package com.makethisbot.bot.command.impl;

import com.makethisbot.bot.command.Command;
import com.makethisbot.bot.util.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Locale;


@Component("helpOrderTypeCommand")
public class HelpOrderTypeCommand implements Command {

    @Autowired
    private MessagesUtil messagesUtil;

    @Override
    public SendMessage doWork(Update update) {
        Locale locale = new Locale(update.getMessage().getFrom().getLanguageCode());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(messagesUtil.getMessageByKey("command.help.order.type", locale));
    }
}
