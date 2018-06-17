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

    private final Map<String, Command> commands = new HashMap<>();

    @Autowired
    @Qualifier("helpOrderTypeCommand")
    private Command helpOrderTypeCommand;

    @PostConstruct
    private void init() {
        commands.put("/help_order_type", helpOrderTypeCommand);
    }

    public boolean isCommandExist(Update update) {
        if (!CollectionUtils.isEmpty(update.getMessage().getEntities())) {
            return !CollectionUtils.isEmpty(getMessageEntities(update));
        }
        return false;
    }

    /**
     *
     * @param update
     * @return command from {@link Update} if command was not registered  or not present in update return null
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Command resolveCommand(Update update) {
        if (!isCommandExist(update)) {
            return null;
        }
        MessageEntity commandMessageEntity = getMessageEntities(update).get(0);
        int startCommandIndex = commandMessageEntity.getOffset();
        int endCommandIndex = commandMessageEntity.getOffset() + commandMessageEntity.getLength();
        String command = update.getMessage().getText().substring(startCommandIndex, endCommandIndex);
        return commands.get(command);
    }

    protected List<MessageEntity> getMessageEntities(Update update) {
        return update.getMessage().getEntities()
                .stream()
                .filter(messageEntity -> messageEntity.getType().equals("bot_command"))
                .collect(toList());
    }
}
