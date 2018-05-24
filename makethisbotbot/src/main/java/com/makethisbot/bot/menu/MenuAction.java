package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.objects.Message;

public interface MenuAction {
    void doAction(Message message);
}
