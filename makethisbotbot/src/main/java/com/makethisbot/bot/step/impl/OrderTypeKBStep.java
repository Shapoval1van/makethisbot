package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.KeyboardStep;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("orderTypeKBEnterStep")
public class OrderTypeKBStep extends KeyboardStep {


    @Autowired
    @Qualifier("endStep")
    public void setStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public SendMessage getUnsuccessSendMessage(Long chatId, Locale locale) {
        return null;
    }

    @Override
    public SendMessage getPromptSendMessage(Long chatId, Locale locale) {
        return null;
    }

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return user.getOrder() != null && !StringUtils.isEmpty(user.getOrder().getType());
    }

    @Override
    public boolean isDataValid(Message message) {
        String text = message.getText();
        return !StringUtils.isEmpty(text); //TODO add more complicated validation;
    }

    @Override
    public User updateUserData(User user, Message message) {
        String type = message.getText();
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
        return "test";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "test";
    }


    public ReplyKeyboardMarkup  getKB() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        return keyboardMarkup;
    }
}
