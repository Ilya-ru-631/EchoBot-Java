package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Эхо-бот. Получает обновления от Telegram и отправляет в чат
 * ответ, сгенерированный в Responder.
 */
public class EchoBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final Responder responder;

    public EchoBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.responder = new Responder();
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
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        String reply = responder.respond(text);
        if (reply == null) {
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(reply);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
