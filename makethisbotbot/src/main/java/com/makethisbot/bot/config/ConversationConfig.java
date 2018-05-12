package com.makethisbot.bot.config;


import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        nameEnterStep.liinckWithNextStep(phoneNameStep)
                .liinckWithNextStep(emailEnterStep)
                .liinckWithNextStep(orderTypeEnterKBStep)
                .liinckWithNextStep(orderDescribeEnterStep);
    }


}
