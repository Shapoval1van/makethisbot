package com.makethisbot.bot.menu;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;

public interface ActionMenuItem extends MenuItem {

    /**
     * should be implements when menu item will bo some action except show sub menu for
     * example add picture, files etc
     */
    void doAction(User user);

    UserRepository getUserRepository();
}
