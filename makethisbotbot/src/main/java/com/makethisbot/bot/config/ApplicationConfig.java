package com.makethisbot.bot.config;

import com.makethisbot.bot.MakeThisBotBot;
import org.springframework.context.annotation.*;


@ComponentScan(basePackages = {"com.makethisbot.bot.*"})
@PropertySource("classpath:application.properties")
@Import({ConversationConfig.class, PersistenceConfig.class})
@Configuration
public class ApplicationConfig {

    @Bean
    public MakeThisBotBot getMakeThisBotBot() {
        return new MakeThisBotBot();
    }
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }


}
