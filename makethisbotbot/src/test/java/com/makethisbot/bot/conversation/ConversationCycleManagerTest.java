package com.makethisbot.bot.conversation;

import com.makethisbot.bot.TestAppConfig;
import com.makethisbot.bot.entity.Order;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.menu.KeyboardMenuItem;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.MenuItemsIds;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UserTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class ConversationCycleManagerTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MenuItem faqMenuItem;

    @Autowired
    protected ConversationCycleManager conversationCycleManager;

    @Autowired
    @Qualifier("nameEnterStep")
    private Step step;

    private final int id = 1;
    private User user;
    private MessageTest message;
    private UserTest userTest;
    private Long chatId = 21312L;
    private String name = "Tester";
    private String phone = "0934582901";

    @Before
    public void setUp() {
        user = new User();
        message = new MessageTest();
        userTest = new UserTest();
        userTest.setId(id);
        message.setText("test");
        message.setChatId(chatId);
        message.setFrom(userTest);
        user.setId(id);
        user.setTelegramUsername("First");
        user.setTelegramUsername("Last");
        user.setLanguageCode("ua");
        userRepository.save(user);
    }

    @After
    public void cleanUp() {
        userRepository.delete(id);
    }

    @Test
    public void processMessage_shouldSavePhone_and_returnSendMessage() {
        user.setName(name);
        userRepository.save(user);
        message.setText(phone);
        Step currentStep = step.getCurrentStep(user);
        SendMessage expectedSendMessage = currentStep.getNextStep().getPromptSendMessage(chatId, new Locale(user.getLanguageCode()));
        SendMessage sendMessage = conversationCycleManager.processMessage(message, user);
        User newUser = userRepository.findOne(id);
        assertEquals(chatId.toString(), sendMessage.getChatId());
        assertEquals(expectedSendMessage.getText(), sendMessage.getText());
        assertEquals(phone, newUser.getPhoneNumber());
    }

    @Test
    public void processMessage_shouldSaveName_and_returnSendMessage() {
        message.setText(name);
        Step currentStep = step.getCurrentStep(user);
        SendMessage expectedSendMessage = currentStep.getNextStep().getPromptSendMessage(chatId, new Locale(user.getLanguageCode()));
        SendMessage sendMessage = conversationCycleManager.processMessage(message, user);
        User newUser = userRepository.findOne(id);
        assertEquals(expectedSendMessage.getText(), sendMessage.getText());
        assertEquals(name, newUser.getName());
    }

    @Test
    public void processMessage_shouldNotSavePhone_and_returnSendMessage() {
        user.setName(name);
        userRepository.save(user);
        message.setText(phone + 42);
        Step currentStep = step.getCurrentStep(user);
        SendMessage expectedSendMessage = currentStep.getUnsuccessSendMessage(chatId, new Locale(user.getLanguageCode()));
        SendMessage sendMessage = conversationCycleManager.processMessage(message, user);
        User newUser = userRepository.findOne(id);
        assertEquals(chatId.toString(), sendMessage.getChatId());
        assertEquals(expectedSendMessage.getText(), sendMessage.getText());
        assertNull(newUser.getPhoneNumber());
    }

    @Test
    public void processMessage_shouldSendRootMenu() {
        Order order = new Order();
        order.setType("1");
        ConversationCycleManager conversationCycleManagerSpy = Mockito.spy(conversationCycleManager);
        doReturn(new SendMessage()).when(conversationCycleManagerSpy).sendRootMenu(chatId);
        user.setName(name);
        user.setPhoneNumber(phone);
        user.setEmail("test@gmail.com");
        user.setOrder(order);
        userRepository.save(user);
        message.setText(phone);
        conversationCycleManagerSpy.processMessage(message, user);
        verify(conversationCycleManagerSpy, times(1)).sendRootMenu(chatId);
    }

    @Test
    public void processMessage_shouldSendFaqMenu() {
        String messageText = String.format(KeyboardMenuItem.FORMAT, MenuItemsIds.FAQ_MENU_ITEM_ID, "text");
        Order order = new Order();
        order.setType("1");
        order.setDescribe("dfgdsfgsd");
        ConversationCycleManager conversationCycleManagerSpy = Mockito.spy(conversationCycleManager);
        user.setName(name);
        user.setPhoneNumber(phone);
        user.setEmail("test@gmail.com");
        user.setOrder(order);
        userRepository.save(user);
        message.setText(messageText);
        SendMessage sendMessage = conversationCycleManagerSpy.processMessage(message, user);
        verify(conversationCycleManagerSpy, times(1)).sendMenu(chatId, messageText);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup)sendMessage.getReplyMarkup();
        assertEquals(faqMenuItem.getKeyboardRowList().size(), replyKeyboardMarkup.getKeyboard().size());
    }
}