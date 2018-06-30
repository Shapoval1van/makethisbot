package com.makethisbot.bot.config;


import com.makethisbot.bot.entity.OrderStatus;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.FieldToChange;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.impl.ChangeDataActionMenuItem;
import com.makethisbot.bot.menu.impl.ContainerMenuItemImpl;
import com.makethisbot.bot.menu.impl.MenuItemImpl;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.menu.util.MenuUtil;
import com.makethisbot.bot.util.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_DUE_DATE;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_EMAIL;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_NAME;
import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID_PHONE;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_QUESTION1_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_QUESTION2_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.ROOT_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.SHOW_ORDER_STATUS_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.SHOW_USER_DATA_MENU_ITEM;
import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;

@ComponentScan(basePackages = {"com.makethisbot.bot"})
@PropertySource("classpath:application.properties")
@Configuration
public class ConversationConfig {

    Map<String, MenuItem> backButtonsMap = new HashMap<>();

    @Autowired
    @Qualifier("nameEnterStep")
    Step nameEnterStep;

    @Autowired
    @Qualifier("phoneEnterStep")
    Step phoneNameStep;

    @Autowired
    @Qualifier("emailEnterStep")
    Step emailEnterStep;

    @Autowired
    @Qualifier("orderTypeEnterKBStep")
    Step orderTypeEnterKBStep;

    @Autowired
    @Qualifier("orderDescribeEnterStep")
    Step orderDescribeEnterStep;

    @Autowired
    @Qualifier("orderDueDateStep")
    Step orderDueDateStep;

    @Autowired
    @Qualifier("endStep")
    Step endStep;

    @Autowired
    MessagesUtil messagesUtil;

    @PostConstruct
    public void initConversation() {
        nameEnterStep.linkWithNextStep(phoneNameStep)
                .linkWithNextStep(emailEnterStep)
                .linkWithNextStep(orderTypeEnterKBStep)
                .linkWithNextStep(orderDescribeEnterStep)
                .linkWithNextStep(orderDueDateStep)
                .linkWithNextStep(endStep);
    }

    //======================================================================================
    //menu config
    //======================================================================================

    @Bean
    public LayoutManager rootMenuItemLayout() {
        return new LayoutManager()
                .addRow(1)
                .addRow(1)
                .addRow(1)
                .addRow(1);
    }

    @Bean
    public LayoutManager faqMenuItemLayout() {
        return new LayoutManager()
                .addRow(1)
                .addRow(1);
    }

    @Bean
    public LayoutManager changeDataMenuItemLayout() {
        return new LayoutManager()
                .addRow(2)
                .addRow(1)
                .addRow(1);
    }

    @Bean
    public MenuItem rootMenuItem() {
        MenuItem rootMenuItem = new ContainerMenuItemImpl("menu.root",
                ROOT_MENU_ITEM_ID.getId(),
                "menu.root.button",
                "",
                Arrays.asList(changeDataMenuItemContainer(),
                        showOrderStatusMenuItem(),
                        faqMenuItemContainer(),
                        userDataMenuItem()),
                rootMenuItemLayout());
        new MenuUtil().addBackButtons(rootMenuItem, backButtonsMap);
        return rootMenuItem;
    }

    @Bean
    public MenuItem changeDataMenuItemContainer() {
        return new ContainerMenuItemImpl("menu.change.data",
                CHANGE_DATA_MENU_ITEM_ID.getId(),
                "menu.change.data.button",
                TO_ROOT_BACK_BUTTON_ID.getId(),
                Arrays.asList(changeDataMenuItemName(),
                        changeDataMenuItemPhone(),
                        changeDataMenuItemEmail(),
                        changeDataMenuItemDueDate()),
                changeDataMenuItemLayout());
    }

    @Bean
    public MenuItem changeDataMenuItemName() {
        return new ChangeDataActionMenuItem("menu.change.data.prompt",
                CHANGE_DATA_MENU_ITEM_ID_NAME.getId(),
                "menu.change.data.name",
                FieldToChange.NAME);
    }

    @Bean
    public MenuItem changeDataMenuItemPhone() {
        return new ChangeDataActionMenuItem("menu.change.data.prompt",
                CHANGE_DATA_MENU_ITEM_ID_PHONE.getId(),
                "menu.change.data.phone",
                FieldToChange.PHONE);
    }

    @Bean
    public MenuItem changeDataMenuItemEmail() {
        return new ChangeDataActionMenuItem("menu.change.data.prompt",
                CHANGE_DATA_MENU_ITEM_ID_EMAIL.getId(),
                "menu.change.data.email",
                FieldToChange.EMAIL);
    }

    @Bean
    public MenuItem changeDataMenuItemDueDate() {
        return new ChangeDataActionMenuItem("menu.change.data.prompt",
                CHANGE_DATA_MENU_ITEM_ID_DUE_DATE.getId(),
                "menu.change.data.due.date",
                FieldToChange.DUE_DATE);
    }

    @Bean
    public MenuItem showOrderStatusMenuItem() {
        return new MenuItemImpl(SHOW_ORDER_STATUS_MENU_ITEM_ID.getId(),
                "menu.show.order.status.button") {
            @Override
            public SendMessage getSendMessage(User user) {
                Locale locale = new Locale(user.getLanguageCode());
                SendMessage sendMessage = getSendMessage(locale);
                OrderStatus orderStatus = user.getOrder().getOrderStatus();
                StringBuffer stringBuffer = new StringBuffer()
                        .append(messagesUtil.getMessageByKey("status.message", locale))
                        .append(messagesUtil.getMessageByKey(orderStatus.getKey(), locale));
                sendMessage.setText(stringBuffer.toString());
                return sendMessage;
            }
        };
    }

    @Bean
    public MenuItem userDataMenuItem() {
        return new MenuItemImpl(SHOW_USER_DATA_MENU_ITEM.getId(),
                "menu.show.user.data") {
            @Override
            public SendMessage getSendMessage(User user) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Locale locale = new Locale(user.getLanguageCode());
                SendMessage sendMessage = getSendMessage(locale);
                StringBuffer stringBuffer = new StringBuffer()
                        .append(messagesUtil.getMessageByKey("menu.show.user.data.name", locale))
                        .append(" ")
                        .append(user.getName())
                        .append(messagesUtil.getMessageByKey("menu.show.user.data.phone", locale))
                        .append(" ")
                        .append(user.getPhoneNumber())
                        .append(messagesUtil.getMessageByKey("menu.show.user.data.email", locale))
                        .append(" ")
                        .append(user.getEmail())
                        .append(messagesUtil.getMessageByKey("menu.show.user.data.due.date", locale))
                        .append(" ")
                        .append(dateFormat.format(user.getOrder().getDueDate()));
                sendMessage.setText(stringBuffer.toString());
                return sendMessage;
            }
        };
    }

    @Bean
    public MenuItem faqMenuItemContainer() {
        MenuItem rootMenuItem = new ContainerMenuItemImpl("menu.faq",
                FAQ_MENU_ITEM_ID.getId(),
                "menu.faq.button",
                TO_ROOT_BACK_BUTTON_ID.getId(),
                Arrays.asList(faqMenuItem1(), faqMenuItem2()),
                faqMenuItemLayout());
        return rootMenuItem;
    }

    @Bean
    public MenuItem faqMenuItem1() {
        return new MenuItemImpl("menu.faq.question1",
                FAQ_MENU_QUESTION1_ITEM_ID.getId(),
                "menu.faq.question1.button");
    }

    @Bean
    public MenuItem faqMenuItem2() {
        return new MenuItemImpl("menu.faq.question2",
                FAQ_MENU_QUESTION2_ITEM_ID.getId(),
                "menu.faq.question2.button");
    }

    @Bean
    public Map<String, MenuItem> backButtonsMap() {
        return backButtonsMap;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
