package com.makethisbot.bot.menu.layout;

import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LayoutManager {

    //each list element represent keyboard row and value of it represent quantity of button in this row
    private List<Integer> placementOrder = new ArrayList<>();

    /**
     *
     * @param buttonQtyInRow each added number show how many button will be in each KeyboardRow
     * @return
     */
    public LayoutManager addRow(int buttonQtyInRow) {
        placementOrder.add(buttonQtyInRow);
        return this;
    }

    public List<KeyboardRow> placeKeyboardButton(List<KeyboardButton> keyboardButtons) throws LayoutInitializationException {
        validateLayout(keyboardButtons);
        LinkedList<KeyboardButton> keyboardButtonQueue = new LinkedList<>(keyboardButtons);
        return placementOrder
                .stream().map(countInRow -> createKeyboardRow(countInRow, keyboardButtonQueue))
                .collect(toList());
    }

    private void validateLayout(List<KeyboardButton> keyboardButtons) throws LayoutInitializationException {
        if (CollectionUtils.isEmpty(placementOrder)) {
            throw new LayoutInitializationException("layout should be not empty");
        }
        Integer sum = placementOrder.stream().reduce(0, (acc, element) -> acc + element);
        if (keyboardButtons.size() != sum) {
            throw new LayoutInitializationException("keyboardButtons qty should be equals to sum buttons in each rows");
        }
    }

    private KeyboardRow createKeyboardRow(int quantity, LinkedList<KeyboardButton> keyboardButtonQueue) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (int i = 0; i < quantity; i++) {
            keyboardRow.add(keyboardButtonQueue.pop());
        }
        return keyboardRow;
    }

}
