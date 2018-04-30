package com.makethisbot.bot.conversation;

import com.makethisbot.bot.entity.User;
import com.makethisbot.bot.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConversationCycleManager {

   @Autowired
   @Qualifier("enterNameStep")
    private Step step;

    public Step determinateStepForUser(User user) {

        Step notCompletedStep = step.getNotCompletedStep(user);
        return notCompletedStep;
    }
}
