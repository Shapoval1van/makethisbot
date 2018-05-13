package com.makethisbot.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class CommandResolver {

    public final Map<String, Command> commands = new HashMap<>();

    @Autowired
    @Qualifier("helpOrderTypeCommand")
    private Command helpOrderTypeCommand;

    @PostConstruct
    private void init() {
        commands.put("/help_order_type", helpOrderTypeCommand);
    }

    public boolean isCommandExist(Update update) {
        if (update.hasMessage() && !CollectionUtils.isEmpty(update.getMessage().getEntities())) {
            return !CollectionUtils.isEmpty(getMessageEntities(update));
        }
        return false;
    }

    public Command resolveCommand(Update update) throws IllegalAccessException, InstantiationException {
        MessageEntity commandMessageEntity = getMessageEntities(update).get(0);
        int startCommandIndex = commandMessageEntity.getOffset();
        int endCommandIndex = commandMessageEntity.getOffset() + commandMessageEntity.getLength();
        String command = update.getMessage().getText().substring(startCommandIndex, endCommandIndex);
        return commands.get(command);
    }

    private List<MessageEntity> getMessageEntities(Update update) {
        return update.getMessage().getEntities()
                .stream()
                .filter(messageEntity -> messageEntity.getType().equals("bot_command"))
                .collect(toList());
    }
}
