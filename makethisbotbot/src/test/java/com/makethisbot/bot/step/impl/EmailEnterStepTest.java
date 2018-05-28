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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
public class EmailEnterStepTest {
    @Autowired
    @Qualifier("emailEnterStep")
    Step emailEnterStep;

    private MessageTest message;

    @Before
    public void setUp() {
        message = new MessageTest();
    }

    @Test
    public void isCurrentStepCompleted() {
        User user = new User();
        user.setEmail("test@gmail.com");
        assertTrue(emailEnterStep.isCurrentStepCompleted(user));
    }

    @Test
    public void isDataValid_shouldFailed() {
        message.setText("043554643634");
        assertFalse(emailEnterStep.isDataValid(message));
    }

    @Test
    public void isDataValid_shouldReturnTrue() {
        message.setText("test@gmail.com");
        assertTrue(emailEnterStep.isDataValid(message));
    }
}