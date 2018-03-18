package com.makethisbot.bot.config;

import com.makethisbot.bot.ConversationCycleManager;
import com.makethisbot.bot.conditional.ConditionalForTheNextStep;
import com.makethisbot.bot.conditional.impl.NameEnterConditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@ComponentScan(basePackages = "com.makethisbot.bot")
public class ConversationConfig {

    @Autowired
    private ConditionalForTheNextStep nameEnterConditional;

    @Bean(name = "transitionalMap")
    public Map<String, ConditionalForTheNextStep> transitionMap(){
        Map<String, ConditionalForTheNextStep> transitionalMap = new LinkedHashMap<>();
        transitionalMap.put("EnterName", new NameEnterConditional());
        transitionalMap.put("EnterPhoneNumber", new NameEnterConditional());
        transitionalMap.put("EnterEmail", new NameEnterConditional());
        transitionalMap.put("EnterSpecification", new NameEnterConditional());
        return transitionalMap;
    }

    @Bean
    public ConversationCycleManager conversationCycleManager() {
        return new ConversationCycleManager(transitionMap());
    }
}
