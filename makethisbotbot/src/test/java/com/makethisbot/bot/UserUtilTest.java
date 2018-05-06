package com.makethisbot.bot;


import com.makethisbot.bot.config.ApplicationConfig;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UpdateTest;
import com.makethisbot.bot.telegram.objects.UserTest;
import com.makethisbot.bot.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class UserUtilTest {
    private final static Integer ID = 1;


    @Autowired
    private UserUtil userUtil;

    @Test
    public void getUserFromTelegramUpdate() {
        UpdateTest update = new UpdateTest();
        MessageTest message = new MessageTest();
        UserTest user = new UserTest();
        user.setFirstName("Test");
        user.setLastName("Tester");
        user.setUserName("mad_tester");
        user.setId(ID);
        user.setLanguageCode("ua");
        message.setFrom(user);
        update.setMessage(message);

        User userNew = userUtil.getUserFromTelegramUpdate(update);
        assertEquals(ID, userNew.getId());
    }


}