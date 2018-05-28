package com.makethisbot.bot.config;

import com.makethisbot.bot.MakeThisBotBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Import({PersistenceConfig.class, ConversationConfig.class})
@Configuration
public class ApplicationConfig {

    @Bean
    public MakeThisBotBot makeThisBotBot() {
        return new MakeThisBotBot();
    }

}
