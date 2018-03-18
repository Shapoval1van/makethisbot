package com.makethisbot.bot;

import com.makethisbot.bot.conditional.ConditionalForTheNextStep;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConversationCycleManager {

    private Map<String, ConditionalForTheNextStep> transitionalMap;

    private String curentStep;

    private String nextStep;

    public ConversationCycleManager(Map<String, ConditionalForTheNextStep> transitionalMap) {
        this.transitionalMap = transitionalMap;
        curentStep = (String) ((LinkedHashMap) this.transitionalMap).keySet().toArray()[0];
//        nextStep =  (String) ((LinkedHashMap) this.transitionalMap).keySet().toArray()[1]; //todo rewrite this
    }
}
