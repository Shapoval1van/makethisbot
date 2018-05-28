package com.makethisbot.bot;


import com.github.fakemongo.Fongo;
import com.makethisbot.bot.config.ApplicationConfig;
import com.makethisbot.bot.config.ConversationConfig;
import com.makethisbot.bot.config.PersistenceConfig;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:application.properties")
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {ApplicationConfig.class, PersistenceConfig.class, ConversationConfig.class})})
@EnableMongoRepositories(basePackages = {"com.makethisbot.bot.repository*"})
public class TestAppConfig extends PersistenceConfig {
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
                .addRow(1);
    }

    @Bean
    public Map<String, MenuItem> backButtonsMap() {
        return new HashMap<>();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
