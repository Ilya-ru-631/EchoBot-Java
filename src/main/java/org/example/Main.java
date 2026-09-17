package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;
import java.util.Properties;

/**
 * Главный класс приложения. Запускает Telegram-бота.
 */

public class Main {
    /**
     * Точка входа. Читает конфиг и регистрирует бота в Telegram API.
     */
    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            config.load(input);
        }

        String botUsername = config.getProperty("bot.username");
        String botToken = config.getProperty("bot.token");

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new EchoBot(botUsername, botToken));

        System.out.println("Bot successfully started!");
    }

    /**
     * Эхо-бот. Отправляет в чат текст полученного сообщения.
     */
    public static class EchoBot extends TelegramLongPollingBot {

        private final String botUsername;
        private final String botToken;

        public EchoBot(String botUsername, String botToken) {
            this.botUsername = botUsername;
            this.botToken = botToken;
        }

        /** Имя пользователя бота. */
        @Override
        public String getBotUsername() {
            return botUsername;
        }

        /** Секретный токен авторизации бота. */
        @Override
        public String getBotToken() {
            return botToken;
        }

        /**
         * Обработчик входящих обновлений.
         * Реализует логику эха для текстовых сообщений.
         */
        @Override
        public void onUpdateReceived(Update update) {
            if (update != null && update.hasMessage() && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}