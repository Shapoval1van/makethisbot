package com.makethisbot.bot;


import com.github.fakemongo.Fongo;
import com.makethisbot.bot.config.ApplicationConfig;
import com.makethisbot.bot.config.ConversationConfig;
import com.makethisbot.bot.config.PersistenceConfig;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.impl.ContainerMenuItemImpl;
import com.makethisbot.bot.menu.impl.MenuItemImpl;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import com.makethisbot.bot.util.MenuUtil;
import com.makethisbot.bot.util.MessagesUtil;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.makethisbot.bot.menu.MenuItemsIds.CHANGE_DATA_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_QUESTION1_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.FAQ_MENU_QUESTION2_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.ROOT_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.SHOW_ORDER_STATUS_MENU_ITEM_ID;
import static com.makethisbot.bot.menu.MenuItemsIds.TO_ROOT_BACK_BUTTON_ID;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {ApplicationConfig.class, PersistenceConfig.class, ConversationConfig.class})})
@EnableMongoRepositories(basePackages = {"com.makethisbot.bot.repository*"})
public class TestAppConfig extends PersistenceConfig {
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
                .linkWithNextStep(endStep);
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Fongo("mongo-test").getMongo();
    }

    @Bean
    public LayoutManager rootMenuItemLayout() {
        return new LayoutManager()
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
    public MenuItem rootMenuItem() {
        MenuItem rootMenuItem = new ContainerMenuItemImpl("menu.root",
                ROOT_MENU_ITEM_ID.getId(),
                "menu.root.button",
                "",
                Arrays.asList(changeDataMenuItem(), showOrderStatusMenuItem(), faqMenuItemContainer()),
                rootMenuItemLayout(),
                messagesUtil);
        ((EndStep) endStep).setRootMenuItem(rootMenuItem);
        new MenuUtil().addBackButtons(rootMenuItem, backButtonsMap);
        return rootMenuItem;
    }

    @Bean
    public MenuItem changeDataMenuItem() {
        return new MenuItemImpl("menu.change.data",
                CHANGE_DATA_MENU_ITEM_ID.getId(),
                "menu.change.data.button",
                messagesUtil);
    }

    @Bean
    public MenuItem showOrderStatusMenuItem() {
        return new MenuItemImpl(SHOW_ORDER_STATUS_MENU_ITEM_ID.getId(),
                "menu.show.order.status.button",
                messagesUtil);
    }

    @Bean
    public MenuItem faqMenuItemContainer() {
        MenuItem rootMenuItem = new ContainerMenuItemImpl("menu.faq",
                FAQ_MENU_ITEM_ID.getId(),
                "menu.faq.button",
                TO_ROOT_BACK_BUTTON_ID.getId(),
                Arrays.asList(faqMenuItem1(), faqMenuItem2()),
                faqMenuItemLayout(),
                messagesUtil);
        return rootMenuItem;
    }

    @Bean
    public MenuItem faqMenuItem1() {
        return new MenuItemImpl("menu.faq.question1",
                FAQ_MENU_QUESTION1_ITEM_ID.getId(),
                "menu.faq.question1.button",
                messagesUtil);
    }

    @Bean
    public MenuItem faqMenuItem2() {
        return new MenuItemImpl("menu.faq.question2",
                FAQ_MENU_QUESTION2_ITEM_ID.getId(),
                "menu.faq.question2.button",
                messagesUtil);
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
