package com.makethisbot.bot.menu;

import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_DUE_DATE;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_EMAIL;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_NAME;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_PHONE;

public enum FieldToChange {
    NAME(CHANGE_DATA_MENU_ITEM_ID_NAME.getId(), "name"),
    PHONE(CHANGE_DATA_MENU_ITEM_ID_PHONE.getId(), "phoneNumber"),
    EMAIL(CHANGE_DATA_MENU_ITEM_ID_EMAIL.getId(), "email"),
    DUE_DATE(CHANGE_DATA_MENU_ITEM_ID_DUE_DATE.getId(), "order.dueDate");

    private String id;
    private String fieldName;

    FieldToChange(String id, String fieldName) {
        this.id = id;
        this.fieldName = fieldName;
    }

    public String getId() {
        return id;
    }

    public String getFieldName() {
        return fieldName;
    }
}
