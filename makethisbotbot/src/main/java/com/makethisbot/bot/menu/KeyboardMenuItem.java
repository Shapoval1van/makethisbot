package com.makethisbot.bot.menu;

import com.makethisbot.bot.menu.layout.LayoutInitializationException;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.util.MessagesUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import java.util.Locale;
import static java.util.stream.Collectors.toList;

public abstract class KeyboardMenuItem implements MenuItem {

    private Logger logger = LoggerFactory.getLogger(KeyboardMenuItem.class);

    public final static String SEPARATOR = " ";
    public final static String FORMAT = "%s" + SEPARATOR + "%s";

    @Autowired
    private MessagesUtil messagesUtil;

    protected List<MenuItem> childMenuItems;

    protected LayoutManager layout;

    protected List<KeyboardRow> keyboardRowList;

    /**
     * here we represent child menu items to keyboard row using {@link #layout}
     */
    @PostConstruct
    protected void init() {
        if (layout == null) { //if layout not init that mean we don't have any childItems
            return;
        }
        try {
            keyboardRowList = layout.placeKeyboardButton(childMenuItems
                    .stream()
                    .map(MenuItem::getKeyboardButton)
                    .collect(toList()));
        } catch (LayoutInitializationException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SendMessage getSendMessage(Locale locale) { //we get SendMessage with already localized text field
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messagesUtil.getMessageByKey(getTextKey(), locale));
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(getLocalizedKeyboardButtons(locale)));
        return sendMessage;
    }

    @Override
    public void addChildItem(MenuItem menuItem) {
        if (childMenuItems == null) {
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
    public String getTextKey() { //default  message text same that button text
        return getButtonTextKey();
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(String.format(FORMAT, getId(), getButtonTextKey()));
    }

    protected List<KeyboardRow> getLocalizedKeyboardButtons(Locale locale) {
        List<KeyboardRow> localizedKeyboardRowList = getKeyboardRowListCopy();
        for (KeyboardRow keyboardRow : localizedKeyboardRowList) {
            for (KeyboardButton keyboardButton : keyboardRow) {
                keyboardButton.setText(messagesUtil.getLocalizedButtonMenuText(keyboardButton.getText(), locale));
            }
        }
        return localizedKeyboardRowList;
    }

    protected List<KeyboardRow> getKeyboardRowListCopy() {
        return keyboardRowList.stream().map(keyboardRow -> keyboardRow.stream().map(keyboardButton ->
                new KeyboardButton(keyboardButton.getText()))
                .collect(new KeyboardRowCollector()))
                .collect(toList());
    }
}
