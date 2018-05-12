package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.TextStep;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

@Component("orderDescribeEnterStep")
public class OrderDescribeEnterStep extends TextStep {

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return user.getOrder() != null && !StringUtils.isEmpty(user.getOrder().getDescribe());
    }

    @Override
    public boolean isDataValid(Message message) {
        String text = message.getText();
        return !StringUtils.isEmpty(text); //TODO add more complicated validation;
    }

    @Override
    public User updateUserData(User user, Message message) {
        String describe = message.getText();
        Order order = user.getOrder();
        if (order == null) {
            order = new Order();
            order.setDescribe(describe);
            user.setOrder(order);
        } else {
            order.setDescribe(describe);
        }
        return user;
    }

    @Override
    public String getPromptMessageKey() {
        return "enter.order.describe.prompt";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "enter.order.describe.unsuccess";
    }
}
