package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.TextStep;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component("orderDueDateStep")
public class OrderDueDateStep extends TextStep {

    private final static String DATE_MATCHER = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private final static int MIN_DAY_QNT_TO_EXECUTE = 3;

    @Override
    public boolean isCurrentStepCompleted(User user) {
        return user.getOrder() != null && !StringUtils.isEmpty(user.getOrder().getDueDate());
    }

    @Override
    public boolean isDataValid(Message message) {
        Instant now = Instant.now(); //current date
        Instant minDate = now.plus(Duration.ofDays(MIN_DAY_QNT_TO_EXECUTE));
        String dateString = message.getText();
        return dateString.matches(DATE_MATCHER) && stringToDate(dateString).after(Date.from(minDate));
    }

    @Override
    public User updateUserData(User user, Message message) {
        String dateString = message.getText();
        Date date = stringToDate(dateString);
        Order order = user.getOrder();
        if (order == null) {
            order = new Order();
            order.setDueDate(date);
            user.setOrder(order);
        } else {
            order.setDueDate(date);
        }
        return user;
    }

    @Override
    public String getPromptMessageKey() {
        return "enter.order.due.date.prompt";
    }

    @Override
    public String getUnSuccessMessageKey() {
        return "enter.order.due.date.unsuccess";
    }

    private Date stringToDate(String dateString) {
        Date outputDate = null;
        List<String> possibleDateFormats = Arrays.asList("dd:MM:yyyy", "dd/MM/yyyy", "dd.MM.yyyy");

        for (String currentFormat : possibleDateFormats) {
            try {
                outputDate = new SimpleDateFormat(currentFormat).parse(dateString);
            } catch (ParseException e) {
                logger.debug("Parse Exception with date {}", dateString);
            }
        }
        return outputDate;
    }

}
