package com.makethisbot.bot.step.impl;

import com.makethisbot.bot.TestAppConfig;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import com.makethisbot.bot.telegram.objects.MessageTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
public class PhoneEnterStepTest {
    @Autowired
    @Qualifier("phoneEnterStep")
    Step phoneNameStep;

    private MessageTest message;

    @Before
    public void setUp() {
        message = new MessageTest();
    }

    @Test
    public void isCurrentStepCompleted() {
        User user = new User();
        user.setPhoneNumber("0934562412");
        assertTrue(phoneNameStep.isCurrentStepCompleted(user));
    }

    @Test
    public void isDataValid_shouldFailed() {
        message.setText("0435546436334");
        assertFalse(phoneNameStep.isDataValid(message));
    }

    @Test
    public void isDataValid_shouldReturnTrue() {
        message.setText("0934567984");
        assertTrue(phoneNameStep.isDataValid(message));
    }

}