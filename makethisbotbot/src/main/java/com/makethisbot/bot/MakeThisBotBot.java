package com.makethisbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MakeThisBotBot extends TelegramLongPollingBot {

    Logger logger = LoggerFactory.getLogger(MakeThisBotBot.class);

    @Value("${tread.count}")
    private int poolCount;

    @Value("${bot.name}")
    private String botName;

    @Value("${token}")
    private String token;

    @Autowired
    private UpdateHandler updateHandler;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(poolCount);
    }

    @Override
    public void onUpdateReceived(Update update) {
        executorService.submit(() -> {
            SendMessage sendMessage = updateHandler.processUpdate(update);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
