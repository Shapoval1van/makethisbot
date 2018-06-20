package com.makethisbot.bot.step;

import com.makethisbot.bot.step.impl.OrderTypeKBStep;
import com.makethisbot.bot.telegram.objects.MessageEntityTest;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UpdateTest;
import com.makethisbot.bot.util.MessagesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cglib.core.Local;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardStepTest {

    protected MessageTest message =  new MessageTest();

    @Mock
    private MessagesUtil messagesUtil;

    @InjectMocks
    private KeyboardStep keyboardStep = spy(new OrderTypeKBStep());


    @Test
    public void isDataValid_should_return_false() throws Exception {
        message.setText("test");
        KeyboardStep keyboardStep = spy(new OrderTypeKBStep());
        assertFalse(keyboardStep.isDataValid(message));
    }

    @Test
    public void isDataValid_should_return_true() throws Exception {
        message.setText("1.test");
        KeyboardStep keyboardStep = spy(new OrderTypeKBStep());
        assertTrue(keyboardStep.isDataValid(message));
    }

    @Test
    public void getUnsuccessSendMessage() throws Exception {
        Locale locale = new Locale("ua");
        String messageKey = "test";
        String messageText = "text";
        message.setText("1.test");
        doReturn(messageKey).when(keyboardStep).getUnSuccessMessageKey();
        when(messagesUtil.getMessageByKey(messageKey, locale)).thenReturn(messageText);
        SendMessage sendMessage = keyboardStep.getUnsuccessSendMessage(1L, locale);
        assertEquals(messageText, sendMessage.getText());
        verify(keyboardStep).getUnSuccessMessageKey();
    }

    @Test
    public void getPromptSendMessage() throws Exception {
        Locale locale = new Locale("ua");
        String messageKey = "test";
        String messageText = "text";
        message.setText("1.test");
        doReturn(messageKey).when(keyboardStep).getPromptMessageKey();
        when(messagesUtil.getMessageByKey(messageKey, locale)).thenReturn(messageText);
        SendMessage sendMessage = keyboardStep.getPromptSendMessage(1L, locale);
        assertEquals(messageText, sendMessage.getText());
        verify(keyboardStep).getPromptMessageKey();
    }
}