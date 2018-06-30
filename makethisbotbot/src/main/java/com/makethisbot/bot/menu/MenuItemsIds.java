package com.makethisbot.bot.menu;

public enum MenuItemsIds {

    ROOT_MENU_ITEM_ID("\uD83C\uDF33"),
    FAQ_MENU_ITEM_ID("\u2753"),
    FAQ_MENU_QUESTION1_ITEM_ID("\uD83D\uDE1E"),
    FAQ_MENU_QUESTION2_ITEM_ID("🕵"),
    SHOW_ORDER_STATUS_MENU_ITEM_ID("\u2705"),

    CHANGE_DATA_MENU_ITEM_ID("\u270F"),
    TO_ROOT_BACK_BUTTON_ID("\uD83D\uDD19"),

    CHANGE_DATA_MENU_ITEM_ID_NAME("\uD83D\uDC66"),
    CHANGE_DATA_MENU_ITEM_ID_PHONE("\uD83D\uDCF1"),
    CHANGE_DATA_MENU_ITEM_ID_EMAIL("✉"),
    CHANGE_DATA_MENU_ITEM_ID_DUE_DATE("\uD83D\uDDD3"),

    SHOW_USER_DATA_MENU_ITEM("\uD83C\uDF93"),

    TO_FAQ_BACK_BUTTON_ID("\u2049");
    private String id;

    MenuItemsIds(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }
}
