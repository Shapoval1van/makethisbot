package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.KeyboardStep;
import com.makethisbot.bot.step.OrderType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.makethisbot.bot.step.OrderType.BUSINESS;
import static com.makethisbot.bot.step.OrderType.DEFAULT;
import static com.makethisbot.bot.step.OrderType.FUN;
import static com.makethisbot.bot.step.OrderType.GAME;
import static com.makethisbot.bot.step.OrderType.ORGANIZER;

@Component("orderTypeEnterKBStep")
public class OrderTypeKBStep extends KeyboardStep {

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return user.getOrder() != null && !StringUtils.isEmpty(user.getOrder().getType());
    }

    @Override
    public User updateUserData(User user, Message message) {
        String messageText = message.getText();
        String[] splittedMessageText = messageText.split(Pattern.quote("."));
        int buttonIndex = Integer.parseInt(splittedMessageText[0]);
        OrderType orderType = findOrderTypeById(buttonIndex);
        String type = orderType.name();

        Order order = user.getOrder();
        if (order == null) {
            order = new Order();
            order.setType(type);
            user.setOrder(order);
        } else {
            order.setType(type);
        }
        return user;
    }

    @Override
    public String getPromptMessageKey() {
        return "enter.order.type.prompt";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "enter.order.type.unsuccess";
    }


    public ReplyKeyboard getKeyboard(Locale locale) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        getKeyboardTitleKey().forEach(key -> {
            String message = messagesUtil.getMessageByKey(key, locale);
            row.add(String.format(FORMAT, getKeyboardTitleKey().indexOf(key) + 1, message));
        });
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public List<String> getKeyboardTitleKey() {
        List<String> keyBoardTitleKey = new ArrayList<>();
        keyBoardTitleKey.add(BUSINESS.getKey());
        keyBoardTitleKey.add(GAME.getKey());
        keyBoardTitleKey.add(FUN.getKey());
        keyBoardTitleKey.add(ORGANIZER.getKey());
        keyBoardTitleKey.add(DEFAULT.getKey());
        return keyBoardTitleKey;
    }

    private OrderType findOrderTypeById(int buttonIndex) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.getId() == buttonIndex) {
                return orderType;
            }
        }
        return DEFAULT;
    }
}
