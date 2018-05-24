package com.makethisbot.bot.config;


import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan(basePackages = {"com.makethisbot.bot"})
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
                .addRow(1);
    }

}
