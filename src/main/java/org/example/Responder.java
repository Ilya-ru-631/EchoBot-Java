package org.example;

/**
 * Формирует ответ бота на входящий текст.
 * Логика эха: если сообщение не пустое — возвращает его же текст.
 */
public class Responder {

    /**
     * @param text текст входящего сообщения
     * @return текст ответа, либо null, если отвечать не на что
     */
    public String respond(String text) {
        if (text != null && !text.isBlank()) {
            return text;
        }
        return null;
    }
}