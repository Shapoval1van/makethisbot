package com.makethisbot.bot.menu;

import java.util.HashMap;
import java.util.Map;

public final class BackButtons {

    private static BackButtons backButtons;
    private Map<String, MenuItem> backButtonsMap = new HashMap<>();

    private BackButtons() {
    }

    public static BackButtons getBackButtonsInstance() {
        if (backButtons == null) {
            backButtons = new BackButtons();
        }
        return backButtons;
    }

    public Map<String, MenuItem> getBackButtonsMap() {
        return backButtonsMap;
    }
}
