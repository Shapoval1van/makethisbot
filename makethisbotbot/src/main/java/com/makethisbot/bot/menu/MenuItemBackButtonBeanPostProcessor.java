package com.makethisbot.bot.menu;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import static com.makethisbot.bot.menu.KeyboardMenuItem.FORMAT;
import static java.lang.String.format;

@Component
public class MenuItemBackButtonBeanPostProcessor implements BeanPostProcessor {

    protected BackButtons backButtons = BackButtons.getBackButtonsInstance();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof MenuItem)) {
            return bean;
        }
        ((MenuItem) bean).getChildMenuItems().forEach(
                childMenuItem -> {
                    if (!StringUtils.isEmpty(childMenuItem.getBackButtonId())) {
                        backButtons.getBackButtonsMap().put(childMenuItem.getBackButtonId(), (MenuItem) bean);
                        KeyboardRow keyboardRow = new KeyboardRow();
                        keyboardRow.add(new KeyboardButton(format(FORMAT, childMenuItem.getBackButtonId(), "menu.back.button")));
                        childMenuItem.getKeyboardRowList().add(keyboardRow);
                    }
                }
        );
        return bean;
    }
}
