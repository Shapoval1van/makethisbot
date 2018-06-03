package com.makethisbot.bot.menu;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class KeyboardRowCollector implements Collector<KeyboardButton, KeyboardRow, KeyboardRow> {
    @Override
    public Supplier<KeyboardRow> supplier() {
        return KeyboardRow::new;
    }

    @Override
    public BiConsumer<KeyboardRow, KeyboardButton> accumulator() {
        return KeyboardRow::add;
    }

    @Override
    public BinaryOperator<KeyboardRow> combiner() {
        return (r1, r2) -> {
            r1.addAll(r2);
            return r1;
        };
    }

    @Override
    public Function<KeyboardRow, KeyboardRow> finisher() {
        return i -> (KeyboardRow) i;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED));
    }
}
