package com.makethisbot.bot.menu.util;

import com.makethisbot.bot.menu.ContainerMenuItem;
import com.makethisbot.bot.menu.MenuItem;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.Map;

import static com.makethisbot.bot.menu.util.Constants.MENU_BUTTON_TEXT_FORMAT;
import static java.lang.String.format;

public class MenuUtil {

    public void addBackButtons(MenuItem rootMenuItem, Map<String, MenuItem> backButtonsMap) {
        LinkedList<MenuItem> queue = new LinkedList<>();
        queue.add(rootMenuItem);
        while (!queue.isEmpty()) {
            MenuItem menuItem = queue.pop();
            if (menuItem instanceof ContainerMenuItem) {
                ContainerMenuItem containerMenuItem = (ContainerMenuItem) menuItem;
                queue.addAll(containerMenuItem.getChildMenuItems());
                containerMenuItem.getChildMenuItems().stream().forEach(childItem -> {
                    if (childItem instanceof ContainerMenuItem && !StringUtils.isEmpty(((ContainerMenuItem) childItem).getBackButtonId())) {
                        ContainerMenuItem containerChildItem = (ContainerMenuItem) childItem;
                        backButtonsMap.put(containerChildItem.getBackButtonId(), containerMenuItem);
                        KeyboardRow keyboardRow = new KeyboardRow();
                        keyboardRow.add(new KeyboardButton(format(MENU_BUTTON_TEXT_FORMAT, containerChildItem.getBackButtonId(), "menu.back.button")));
                        containerChildItem.getKeyboardRowList().add(keyboardRow);
                    }
                });
            }

        }
    }
}
