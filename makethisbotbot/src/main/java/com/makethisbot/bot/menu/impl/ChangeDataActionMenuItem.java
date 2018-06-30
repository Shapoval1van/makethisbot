package com.makethisbot.bot.menu.impl;

import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.FieldToChange;

import java.lang.reflect.Field;

public class ChangeDataActionMenuItem extends ActionMenuItemImpl {

    private final FieldToChange fieldToChange;

    public ChangeDataActionMenuItem(String textKey, String id, String buttonTextKey, FieldToChange fieldToChange) {
        super(textKey, id, buttonTextKey);
        this.fieldToChange = fieldToChange;
    }

    @Override
    public void doAction(User user) {
        try {
            if (fieldToChange.getFieldName().startsWith("order")) {
                String fieldToChangeStr = fieldToChange.getFieldName();
                Field orderField = User.class.getDeclaredField("order");
                orderField.setAccessible(true);
                Order order = (Order) orderField.get(user);
                Field field = Order.class.getDeclaredField(fieldToChangeStr.substring(fieldToChangeStr.indexOf(".") + 1));
                field.setAccessible(true);
                field.set(order, null);
            } else {
                Field field = User.class.getDeclaredField(fieldToChange.getFieldName());
                field.setAccessible(true);
                field.set(user, null);
            }
            getUserRepository().save(user);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Unexpected errors", e);
        }
    }

}
