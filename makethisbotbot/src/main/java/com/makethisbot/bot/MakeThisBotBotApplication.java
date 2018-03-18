package com.makethisbot.bot;

import com.makethisbot.bot.config.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;

public class MakeThisBotBotApplication{

	public static void main(String[] args) {
        registerTelegramApiContext();
		final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		MakeThisBotStarter makeThisBotStarter = applicationContext.getBean(MakeThisBotStarter.class);
		makeThisBotStarter.start();
	}

	private static void registerTelegramApiContext() {
        ApiContextInitializer.init();
    }
}
