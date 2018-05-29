package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.item.RootMenuItem;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import com.makethisbot.bot.util.UserUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

@Component
public class ConversationCycleManager {

    private Logger logger = LoggerFactory.getLogger(ConversationCycleManager.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    @Qualifier("nameEnterStep")
    private Step step;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private RootMenuItem rootMenuItem;

    @Resource
    private Map<String, MenuItem> backButtonsMap;

    public SendMessage processMessage(Message message, User user) {
        Step currentStep = step.getCurrentStep(user);
        if (!(currentStep instanceof EndStep)) {
            return processSteps(currentStep, user, message);
        }
        return sendMenu(message.getChatId(), message.getText());
    }

    protected SendMessage processSteps(Step currentStep, User user, Message message) {
        Locale locale = userUtil.getLocalFromUser(user);
        if (!currentStep.isDataValid(message)) {
            return currentStep.getUnsuccessSendMessage(message.getChatId(), locale);
        }
        currentStep.updateUserData(user, message);
        userRepository.save(user);
        return currentStep.getNextStep().getPromptSendMessage(message.getChatId(), locale);
    }

    protected SendMessage sendMenu(Long chatId, String messageText) {
        int index = messageText.indexOf('.');
        if (index == -1) {
            processWrongMessage(chatId);
        }
        String id = messageText.subSequence(0, index).toString(); //TODO add check
        if (backButtonsMap.keySet().contains(id)) {
            return backButtonsMap.get(id).getSendMessage().setChatId(chatId);
        }
        MenuItem menuItem = findMenuItemById(id);
        return menuItem == null ? processWrongMessage(chatId) : menuItem.getSendMessage().setChatId(chatId);
    }

    protected SendMessage processWrongMessage(Long chatId) {
        logger.warn("wrong menu Id"); //TODO change it
        return new SendMessage(chatId, "Something went wrong please try again");
    }

    /**
     * @param id button Id
     * @return return null if button with this Id don't find
     */
    protected MenuItem findMenuItemById(String id) {
        LinkedList<MenuItem> queue = new LinkedList<>();
        queue.add(rootMenuItem);
        while (!queue.isEmpty()) {
            MenuItem menuItem = queue.pop();
            if (menuItem.getId().equals(id)) {
                return menuItem;
            }
            if (menuItem.getChildMenuItems() != null) {
                queue.addAll(menuItem.getChildMenuItems());
            }
        }
        return null;
    }
}
