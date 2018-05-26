package com.makethisbot.bot.menu;

import com.makethisbot.bot.config.ApplicationConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.Resource;
import java.util.Map;

import static com.makethisbot.bot.menu.KeyboardMenuItem.format;
import static java.lang.String.*;

@Component
public class MenuItemBackButtonBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private Map<String, MenuItem> backButtonsMap;

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
                    if(!StringUtils.isEmpty(childMenuItem.getBackButtonId())) {
                        backButtonsMap.put(childMenuItem.getBackButtonId(), (MenuItem) bean);
                        KeyboardRow keyboardRow = new KeyboardRow();
                        keyboardRow.add(new KeyboardButton(format(format, childMenuItem.getBackButtonId(), "Back")));
                        childMenuItem.getKeyboardRowList().add(keyboardRow);
                    }
                }
        );
        return bean;
    }
}
