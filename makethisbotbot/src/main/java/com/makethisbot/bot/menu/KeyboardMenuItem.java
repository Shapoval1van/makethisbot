package com.makethisbot.bot.menu;

import com.makethisbot.bot.menu.layout.LayoutInitializationException;
import com.makethisbot.bot.menu.layout.LayoutManager;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import java.util.stream.Collectors;

public abstract class KeyboardMenuItem implements MenuItem {

    private Logger logger = LoggerFactory.getLogger(KeyboardMenuItem.class);

    protected final static String format = "%s. %s";

    protected List<MenuItem> childMenuItems;

    protected LayoutManager layout;

    protected List<KeyboardRow> keyboardRowList;

    /**
     * here we represent child menu items to keyboard row in order to add they to next ReplyKeyboardMarkup
     */
    @PostConstruct
    protected void init(){
        if(layout == null){ //if layout not init that mean we don't have any childItems
            return;
        }
        try {
            keyboardRowList = layout.placeKeyboardButton(childMenuItems
                    .stream()
                    .map(MenuItem::getKeyboardButton)
                    .collect(Collectors.toList()));
        } catch (LayoutInitializationException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SendMessage getSendMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(getText());
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(keyboardRowList));
        return sendMessage;
    }

    @Override
    public void addChildItem(MenuItem menuItem) {
        if(childMenuItems == null) {
            childMenuItems = new ArrayList<>();
        }
        childMenuItems.add(menuItem);
    }

    @Override
    public List<MenuItem> getChildMenuItems() {
        if (childMenuItems == null) {
            childMenuItems = new ArrayList<>();
        }
        return childMenuItems;
    }


    @Override
    public List<KeyboardRow> getKeyboardRowList() {
        if (keyboardRowList == null) {
            keyboardRowList = new ArrayList<>();
        }
        return keyboardRowList;
    }

    @Override
    public String getText() {
        return String.format(format, getId(), getButtonText());
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(String.format(format, getId(), getButtonText()));
    }
}
