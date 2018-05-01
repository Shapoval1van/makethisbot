package com.makethisbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class MakeThisBotStarter {

    @Autowired
    private MakeThisBotBot makeThisBotBot;

    public void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(makeThisBotBot);
        } catch (TelegramApiException e) {
            e.printStackTrace(); //TODO add exception handler
        }
    }
}
