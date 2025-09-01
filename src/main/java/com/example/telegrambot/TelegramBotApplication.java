package com.example.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения.
 * Аннотация @SpringBootApplication включает:
 * - Автоконфигурацию Spring Boot
 * - Сканирование компонентов в пакете и подпакетах
 * - Настройку встроенного сервера (Tomcat)
 */
@SpringBootApplication
public class TelegramBotApplication {

    /**
     * Точка входа в приложение.
     * При запуске:
     * 1. Создаётся контекст Spring
     * 2. Все @Component, @Service и др. загружаются
     * 3. Запускается встроенный сервер
     * 4. Выполняется BotConfig.init()
     */
    public static void main(String[] args) {
        // Запускаем Spring Boot приложение
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
