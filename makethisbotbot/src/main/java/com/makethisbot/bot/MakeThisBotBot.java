package com.makethisbot.bot;

import com.makethisbot.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MakeThisBotBot extends TelegramLongPollingBot {

    @Value("${tread.count}")
    private int poolCount;

    @Autowired
    private UserRepository userRepository;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(poolCount);
    }

    @Override
    public void onUpdateReceived(Update update) {
      executorService.submit(() -> {
          User user = new User();
          User user1 = new User();
          user.setLastName(update.getMessage().getText());
          user1.setFirstName(update.getMessage().getText());
          userRepository.save(user1);
      });
// some operations
//        String result = future.get();
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
