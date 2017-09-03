package com.odayny.telegram.papugabot.validator;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GodValidatorImpl implements GodValidator {
    @Value("${gods.id}")
    private String godId;
    private TelegramBot bot;

    @Autowired
    public GodValidatorImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void validate(Update update) {
        if (!StringUtils.equals(godId, update.message().from().id().toString())) {
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Hell no!");
            bot.execute(sendMessage);
            throw new IllegalArgumentException(String.format("Not so cool %d", update.message().from().id()));
        }
    }
}
