package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface ContainerMenuItem extends MenuItem {


    /**
     * @return list of {@link MenuItem} witch contains button that will be show after click on current one
     */
    List<MenuItem> getChildMenuItems();

    /**
     * @return list of {@link KeyboardRow} which created based on {@link com.makethisbot.bot.menu.layout.LayoutManager} and
     * {@link #getChildMenuItems()} when {@link MenuItem} will be initialized
     */
    List<KeyboardRow> getKeyboardRowList();

    /**
     * @return backButton id if return empty string its mean that back button not allowed
     */
    String getBackButtonId();

    void setBackButtonId(String backButtonId);

    void setChildMenuItems(List<MenuItem> childMenuItem);
}
