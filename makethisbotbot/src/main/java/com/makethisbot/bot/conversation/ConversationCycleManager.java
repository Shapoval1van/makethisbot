package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.ContainerMenuItem;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.step.impl.EndStep;
import com.makethisbot.bot.util.MessagesUtil;
import com.makethisbot.bot.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import static com.makethisbot.bot.menu.util.Constants.BUTTON_TEXT_SEPARATOR;

@Component
@Slf4j
public class ConversationCycleManager {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    @Qualifier("nameEnterStep")
    private Step step;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private MenuItem rootMenuItem;

    @Autowired
    private MessagesUtil messagesUtil;

    @Resource
    private Map<String, MenuItem> backButtonsMap;

    public SendMessage processMessage(Message message, User user) {
        Step currentStep = step.getCurrentStep(user);
        if (!(currentStep instanceof EndStep)) {
            return processSteps(currentStep, user, message);
        }
        Locale locale = userUtil.getLocalFromUser(user);
        return processMenu(message.getChatId(), message.getText(), locale, user);
    }

    protected SendMessage processSteps(Step currentStep, User user, Message message) {
        Locale locale = userUtil.getLocalFromUser(user);
        if (!currentStep.isDataValid(message)) {
            return currentStep.getUnsuccessSendMessage(message.getChatId(), locale);
        }
        currentStep.updateUserData(user, message);
        userRepository.save(user);
        if (isAllStepCompleted(currentStep, user)) {
            return rootMenuItem.getSendMessage(locale)
                    .setText(messagesUtil.getMessageByKey("menu.change.data.successful", new Locale(user.getLanguageCode())))
                    .setChatId(message.getChatId());
        }
        return currentStep.getNextStep().getPromptSendMessage(message.getChatId(), locale);
    }

    protected SendMessage processMenu(Long chatId, String messageText, Locale locale, User user) {
        int index = messageText.indexOf(BUTTON_TEXT_SEPARATOR);
        if (index == -1) {
            return processWrongMessage(chatId, messageText, locale);
        }
        String id = messageText.subSequence(0, index).toString();
        MenuItem menuItem = findMenuItemById(id);
        return menuItem == null ? processWrongMessage(chatId, messageText, locale) : menuItem.getSendMessage(user).setChatId(chatId);
    }

    protected boolean isAllStepCompleted(Step currentStep, User user) {
        while (!(currentStep instanceof EndStep)) {
            if (!currentStep.isCurrentStepCompleted(user)) {
                return false;
            }
            currentStep = currentStep.getNextStep();
        }
        return true;
    }

    /**
     * @param id button Id
     * @return return null if button with this Id don't find
     */
    protected MenuItem findMenuItemById(String id) {
        if (backButtonsMap.keySet().contains(id)) {
            return backButtonsMap.get(id);
        }
        LinkedList<MenuItem> queue = new LinkedList<>();
        queue.add(rootMenuItem);
        while (!queue.isEmpty()) {
            MenuItem menuItem = queue.pop();
            if (menuItem.getId().equals(id)) {
                return menuItem;
            }
            if (menuItem instanceof ContainerMenuItem) {
                queue.addAll(((ContainerMenuItem) menuItem).getChildMenuItems());
            }
        }
        return null;
    }

    protected SendMessage processWrongMessage(Long chatId, String messageText, Locale locale) {
        log.warn("wrong menu Id was sanded from chat - {}, message - {}", chatId, messageText);
        return new SendMessage(chatId, messagesUtil.getMessageByKey("menu.error", locale));
    }
}
