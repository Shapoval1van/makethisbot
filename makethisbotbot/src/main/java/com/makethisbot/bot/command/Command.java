package com.makethisbot.bot.command;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface Command {
    SendMessage doWork(Update update);
}
