package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Главный класс приложения. Запускает Telegram-бота.
 */
public class Main {

    /**
     * Точка входа. Инициализирует и регистрирует бота в Telegram API.
     */

    public static void main(String[] args) {
        System.out.println("Starting EchoBot");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new EchoBot());

            System.out.println("Bot successfully started!");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Эхо-бот. Отправляет в чат текст полученного сообщения.
     */

    public static class EchoBot extends TelegramLongPollingBot {

        private static final String BOT_USERNAME = "Echo_ilya_252201_bot";
        private static final String BOT_TOKEN = "8325504457:AAHYBhaIpnaRAl_M0WVyQuotC7x4_PUBFrY";

        /**  Имя пользователя бота. */
        @Override
        public String getBotUsername() {
            return BOT_USERNAME;
        }

        /** Секретный токен авторизации бота. */
        @Override
        public String getBotToken() {
            return BOT_TOKEN;
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