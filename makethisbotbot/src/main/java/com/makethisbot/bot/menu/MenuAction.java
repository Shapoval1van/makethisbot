package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.objects.Message;

public interface MenuAction {

    /**
     * should be implements when menu item will bo some action except show sub menu for
     * example add picture, files etc
     * @param message
     */
    void doAction(Message message);
}
