package com.odayny.telegram.papugabot.validator;

import com.pengrad.telegrambot.model.Update;

public interface PermissionsValidator {
    void validateWithResponse(Update update);
}
