package com.makethisbot.bot.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"com.makethisbot.bot.*"})
@EnableMongoRepositories(basePackages = {"com.makethisbot.bot.*"})
public class PersistenceConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }

}
