package com.makethisbot.bot.config;


import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ComponentScan(basePackages = {"com.makethisbot.bot"})
@PropertySource("classpath:application.properties")
@Configuration
public class ConversationConfig {

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
