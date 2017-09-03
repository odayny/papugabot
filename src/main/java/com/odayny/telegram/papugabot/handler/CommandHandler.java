package com.odayny.telegram.papugabot.handler;

import com.pengrad.telegrambot.model.Update;

public interface CommandHandler {
    void executeCommand(Update update);
}
