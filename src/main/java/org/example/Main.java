package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Главный класс приложения. Запускает Telegram-бота.
 */
public class Main {

    /**
     * Точка входа.
     */
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        Properties config = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Не найден config.properties в resources");
            }
            config.load(input);
        }

        String botUsername = config.getProperty("bot.username");

        // Токен: сначала переменная окружения BOT_TOKEN, если не задана — из файла
        String envToken = System.getenv("BOT_TOKEN");
        String botToken = (envToken != null && !envToken.isBlank())
                ? envToken
                : config.getProperty("bot.token");

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new EchoBot(botUsername, botToken));

        System.out.println("Bot successfully started!");
    }
}