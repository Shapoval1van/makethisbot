package com.makethisbot.bot;

import com.makethisbot.bot.command.Command;
import com.makethisbot.bot.command.CommandResolver;
import com.makethisbot.bot.command.impl.HelpOrderTypeCommand;
import com.makethisbot.bot.conversation.ConversationCycleManager;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.repository.UserRepository;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UpdateTest;
import com.makethisbot.bot.telegram.objects.UserTest;
import com.makethisbot.bot.util.MessagesUtil;
import com.makethisbot.bot.util.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversationCycleManager conversationCycleManager;

    @Mock
    private UserUtil userUtil;

    @Mock
    private MessagesUtil messagesUtil;

    @Mock
    private CommandResolver commandResolver;

    @InjectMocks
    protected UpdateHandler updateHandler = new UpdateHandler();

    private UpdateTest updateTest;
    private MessageTest messageTest;
    private UserTest userTest;

    @Before
    public void setUp() {
        updateTest = new UpdateTest();
        messageTest = new MessageTest();
        userTest = new UserTest();
        userTest.setId(1);
        messageTest.setChatId(1L);
        messageTest.setFrom(userTest);
        updateTest.setMessage(messageTest);
        when(userUtil.getUserIdFromUpdate(updateTest)).thenReturn(userTest.getId());
    }

    @Test
    public void processUpdate_shouldDoCommand() throws Exception {
        messageTest.setText("/some_command");
        Command command = mock(HelpOrderTypeCommand.class);
        when(commandResolver.resolveCommand(updateTest)).thenReturn(command);
        updateHandler.processUpdate(updateTest);
        verify(command).doWork(updateTest);
    }

    @Test
    public void processUpdate_shouldSaveUserAndSendWelcomeMessage() throws Exception {
        messageTest.setText("/start");
        User newUser = new User();
        Locale locale = new Locale("ua");
        when(userRepository.findOne(1)).thenReturn(null);
        when(userUtil.getUserFromTelegramUpdate(updateTest)).thenReturn(newUser);
        when(userUtil.getLocalFromUser(newUser)).thenReturn(locale);
        when(messagesUtil.getMessageByKey("welcome.message", locale)).thenReturn("Hi!!!!!");
        SendMessage sendMessage = updateHandler.processUpdate(updateTest);
        verify(userRepository).save(newUser);
        assertEquals("Hi!!!!!", sendMessage.getText());
    }

    @Test
    public void processUpdate_shouldProcessStep() throws Exception {
        messageTest.setText("/start");
        User newUser = new User();
        when(userUtil.getUserIdFromUpdate(updateTest)).thenReturn(userTest.getId());
        when(userRepository.findOne(userTest.getId())).thenReturn(newUser);
        updateHandler.processUpdate(updateTest);
        verify(conversationCycleManager).processMessage(messageTest, newUser);
    }
}