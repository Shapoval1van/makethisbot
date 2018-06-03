package com.makethisbot.bot.menu;

public enum MenuItemsIds {

    ROOT_MENU_ITEM_ID("\uD83C\uDF33"),
    FAQ_MENU_ITEM_ID("\u2753"),
    FAQ_MENU_QUESTION1_ITEM_ID("\uD83D\uDE1E"),
    SHOW_ORDER_STATUS_MENU_ITEM_ID("\u2705"),

    CHANGE_DATA_MENU_ITEM_ID("\u270F"),
    TO_ROOT_BACK_BUTTON_ID("\uD83D\uDD19"),

    TO_FAQ_BACK_BUTTON_ID("\u2049");

    private String id;

    MenuItemsIds(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

    //  public static String TO_ROOT_BACK_BUTTON_ID = "\u2934";
}
