package com.makethisbot.bot.util;

import com.makethisbot.bot.TestAppConfig;
import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.telegram.objects.MessageTest;
import com.makethisbot.bot.telegram.objects.UpdateTest;
import com.makethisbot.bot.telegram.objects.UserTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
public class UserUtilTest {
    private final static Integer ID = 1;

    @Autowired
    private UserUtil userUtil;

    private UpdateTest update;

    private MessageTest message;

    @Before
    public void setUp() {
        update = new UpdateTest();
        message = new MessageTest();
        UserTest user = new UserTest();
        user.setFirstName("Test");
        user.setLastName("Tester");
        user.setUserName("mad_tester");
        user.setId(ID);
        user.setLanguageCode("ua");
        message.setFrom(user);
        update.setMessage(message);
    }

    @Test
    public void getUserFromTelegramUpdateTest() {
        User userNew = userUtil.getUserFromTelegramUpdate(update);
        assertEquals(ID, userNew.getId());
        assertEquals("Tester", userNew.getTelegramLastName());
        assertEquals("Test", userNew.getTelegramFirsName());
    }

    @Test
    public void getUserIdFromUpdate() throws Exception {
        assertEquals(ID.intValue(), userUtil.getUserIdFromUpdate(update));
    }

}