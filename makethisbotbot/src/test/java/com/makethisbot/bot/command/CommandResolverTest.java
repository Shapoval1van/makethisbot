package com.makethisbot.bot.command;

import com.makethisbot.bot.telegram.objects.MessageEntityTest;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UpdateTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.telegrambots.api.objects.MessageEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class CommandResolverTest {

    protected UpdateTest update = new UpdateTest();
    protected MessageTest message =  new MessageTest();

    @Before
    public void setUp() {
        update = new UpdateTest();
        update.setMessage(message);
        message.setEntities(Arrays.asList(new MessageEntityTest("bot_command", 0, "/command".length()),
                new MessageEntityTest("ergdfg", 0, "/test".length())));
        message.setText("/command");
    }

    @Test
    public void getMessageEntities() throws Exception {
        CommandResolver commandResolver = new CommandResolver();
        List<MessageEntity> messageEntities = commandResolver.getMessageEntities(update);
        assertEquals(1, messageEntities.size());
    }
}