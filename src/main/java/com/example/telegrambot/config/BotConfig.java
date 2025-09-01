package com.example.telegrambot.config;

import com.example.telegrambot.bot.TelegramBot;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Конфигурационный класс для регистрации бота в Telegram API.
 * Помечен как @Configuration — Spring сканирует его при старте.
 */
@Configuration
public class BotConfig {

    // Бот автоматически внедряется через DI
    private final TelegramBot telegramBot;

    public BotConfig(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод выполняется после полной инициализации Spring-контекста.
     * Регистрирует бота в Telegram через TelegramBotsApi.
     */
    @EventListener(ContextRefreshedEvent.class)
    public void init() throws TelegramApiException {
        // Создаём API-объект для работы с Telegram
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        // Регистрируем бота — теперь он начнёт получать сообщения
        botsApi.registerBot(telegramBot);
    }
}

