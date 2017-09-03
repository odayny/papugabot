package com.odayny.telegram.papugabot.handler;

import com.pengrad.telegrambot.model.Update;

import java.io.IOException;

public interface UpdateHandler {
    void handleUpdate(Update update) throws IOException;
}
