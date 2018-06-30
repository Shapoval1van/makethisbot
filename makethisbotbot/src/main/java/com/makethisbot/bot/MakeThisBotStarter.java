package com.makethisbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;

@Component
@Slf4j
public class MakeThisBotStarter {

    @Autowired
    private MakeThisBotBot makeThisBotBot;

    public void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(makeThisBotBot);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
