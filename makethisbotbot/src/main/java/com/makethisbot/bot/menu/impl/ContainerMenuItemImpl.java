package com.makethisbot.bot.menu.impl;

import com.makethisbot.bot.menu.ContainerMenuItem;
import com.makethisbot.bot.menu.MenuItem;
import com.makethisbot.bot.menu.layout.LayoutInitializationException;
import com.makethisbot.bot.menu.layout.LayoutManager;
import com.makethisbot.bot.menu.util.KeyboardRowCollector;
import com.makethisbot.bot.util.MessagesUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

import static com.makethisbot.bot.menu.util.Constants.MENU_BUTTON_TEXT_FORMAT;
import static java.util.stream.Collectors.toList;

public class ContainerMenuItemImpl extends MenuItemImpl implements ContainerMenuItem {

    protected List<MenuItem> childMenuItems;

    protected LayoutManager layout;

    protected List<KeyboardRow> keyboardRowList;

    protected String backButtonId;

    public ContainerMenuItemImpl(@NotNull String textKey,
                                 @NotNull String id,
                                 @NotNull String buttonTextKey,
                                 @NotNull String backButtonId,
                                 @NotNull List<MenuItem> childMenuItems,
                                 @NotNull LayoutManager layout,
                                 @NotNull MessagesUtil messagesUtil) {
        super(textKey, id, buttonTextKey, messagesUtil);
        this.layout = layout;
        this.childMenuItems = childMenuItems;
        this.backButtonId = backButtonId;
        init();
    }


    /**
     * here we represent child menu items to keyboard row using {@link #layout}
     */
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
    public SendMessage getSendMessage(Locale locale) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messagesUtil.getMessageByKey(getTextKey(), locale));
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(getLocalizedKeyboardButtons(locale)));
        return sendMessage;
    }

    @Override
    public List<MenuItem> getChildMenuItems() {
        if (childMenuItems == null) {
            childMenuItems = new ArrayList<>();
        }
        return childMenuItems;
    }

    @Override
    public void setChildMenuItems(List<MenuItem> childMenuItems) {
        this.childMenuItems = childMenuItems;
    }

    @Override
    public List<KeyboardRow> getKeyboardRowList() {
        if (keyboardRowList == null) {
            keyboardRowList = new ArrayList<>();
        }
        return keyboardRowList;
    }

    @Override
    public KeyboardButton getKeyboardButton() {
        return new KeyboardButton(String.format(MENU_BUTTON_TEXT_FORMAT, getId(), getButtonTextKey()));
    }

    @Override
    public String getBackButtonId() {
        return backButtonId;
    }

    @Override
    public void setBackButtonId(String backButtonId) {
        this.backButtonId = backButtonId;
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
