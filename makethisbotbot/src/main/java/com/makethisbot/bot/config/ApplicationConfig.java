package com.makethisbot.bot.config;

import com.makethisbot.bot.MakeThisBotBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


@ComponentScan(basePackages = {"com.makethisbot.bot.*"})
@PropertySource("classpath:application.properties")
@Import({ConversationConfig.class, PersistenceConfig.class})
@Configuration
public class ApplicationConfig {

    @Bean
    public MakeThisBotBot getMakeThisBotBot() {
        return new MakeThisBotBot();
    }

}
