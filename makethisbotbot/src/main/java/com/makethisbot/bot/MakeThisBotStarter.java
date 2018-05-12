package com.makethisbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;

@Component
public class MakeThisBotStarter {

    Logger logger = LoggerFactory.getLogger(MakeThisBotStarter.class);

    @Autowired
    private MakeThisBotBot makeThisBotBot;

    public void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(makeThisBotBot);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
