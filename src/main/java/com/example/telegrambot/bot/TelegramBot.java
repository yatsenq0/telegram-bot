package com.example.telegrambot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Основной класс Telegram-бота.
 * Наследуется от TelegramLongPollingBot — режим опроса (polling).
 * Помечен как @Component — Spring создаст его при старте.
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    // Логгер для записи событий (лучше, чем System.out)
    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    // Токен и username подставляются из application.yml
    @Value("\${bot.token}")
    private String botToken;

    @Value("\${bot.username}")
    private String botUsername;

    /**
     * Метод вызывается при каждом новом сообщении от пользователя.
     *
     * @param update — объект, содержащий информацию о сообщении
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли сообщение и текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getFrom().getFirstName();

            // Логируем входящее сообщение
            log.info("Получено от {} (@{}): {}", chatId,
                    update.getMessage().getFrom().getUserName(), messageText);

            // Создаём объект для отправки сообщения
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString()); // chatId должен быть строкой

            // Обработка команды /start
            if ("/start".equals(messageText)) {
                message.setText("Привет, " + firstName + "! 👋\n" +
                               "Я — учебный бот на Spring Boot.\n" +
                               "Пока я умею только отвечать, но скоро научусь многому!");
            } else {
                // Эхо-ответ + подсказка
                message.setText("Вы написали: " + messageText + "\n" +
                               "Попробуйте команду /start");
            }

            // Отправляем сообщение
            try {
                execute(message);
                log.info("Ответ отправлен в чат {}", chatId);
            } catch (TelegramApiException e) {
                // Логируем ошибку, если отправка не удалась
                log.error("Ошибка при отправке сообщения в чат {}", chatId, e);
            }
        }
    }

    /**
     * Возвращает username бота (обязательный метод)
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Возвращает токен бота (обязательный метод)
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}
