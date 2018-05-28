package com.makethisbot.bot;


import com.github.fakemongo.Fongo;
import com.makethisbot.bot.step.Step;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;

@ComponentScan(basePackages = {"com.makethisbot.bot"})
@Configuration
@EnableMongoRepositories(basePackages = {"com.makethisbot.bot.*"})
public class TestAppConfig extends AbstractMongoConfiguration {
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
}
