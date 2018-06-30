package com.makethisbot.bot;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MakeThisBotBot extends TelegramLongPollingBot {

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
            try {
                SendMessage sendMessage = updateHandler.processUpdate(update);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Was occurred telegram telegram api exception. userId = {}", update.getMessage().getFrom().getId());
                log.error(e.getMessage(), e);
            } catch (Exception e) {
                log.error("UserId =  {}", update.getMessage().getFrom().getId());
                log.error(e.getMessage(), e);
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
