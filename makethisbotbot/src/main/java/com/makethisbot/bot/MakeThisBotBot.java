package com.makethisbot.bot;

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

    @Value("${tread.count}")
    private int poolCount;

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
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getBotUsername() {
        return "MakeThisBot";
    }

    @Override
    public String getBotToken() {
        return "592821253:AAH-wcAMDC3h0uRNLrvd36xH37f2E4aTL0I";
    }

}
