package com.makethisbot.bot;

import com.makethisbot.bot.command.Command;
import com.makethisbot.bot.command.CommandResolver;
import com.makethisbot.bot.conversation.ConversationCycleManager;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.util.MessagesUtil;
import com.makethisbot.bot.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Component
public class UpdateHandler {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private ConversationCycleManager conversationCycleManager;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private MessagesUtil messagesUtil;

    @Autowired
    private CommandResolver commandResolver;

    protected Logger logger = LoggerFactory.getLogger(UpdateHandler.class);

    public SendMessage processUpdate(Update update) {
        Message message = update.getMessage();
        Integer userId = userUtil.getUserIdFromUpdate(update);
        if(commandResolver.isCommandExist(update)) {
            try {
                Command command = commandResolver.resolveCommand(update);
                return command.doWork(update);
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error("Can't create instance of Command object", e);
            }
        }
        User user = userRepository.findOne(userId);
        if (user == null) {
            user = userUtil.getUserFromTelegramUpdate(update);
            userRepository.save(user);
            String welcomeMessageText = messagesUtil.getMessageByKey("welcome.message", userUtil.getLocalFromUser(user));
            return new SendMessage(update.getMessage().getChatId(), welcomeMessageText);
        } else if (message != null && message.hasText()) {
            SendMessage sendMessage = conversationCycleManager.processMessage(message, user);
            return sendMessage;
        }
        return new SendMessage(message.getChatId(), "Something went wrong");
    }
}
