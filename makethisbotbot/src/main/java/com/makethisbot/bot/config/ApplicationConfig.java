package com.makethisbot.bot.config;

import com.makethisbot.bot.MakeThisBotBot;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.step.Step;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@PropertySource("classpath:application.properties")
@Import({PersistenceConfig.class, ConversationConfig.class})
@Configuration
public class ApplicationConfig {

    @Bean
    public MakeThisBotBot makeThisBotBot() {
        return new MakeThisBotBot();
    }

}
