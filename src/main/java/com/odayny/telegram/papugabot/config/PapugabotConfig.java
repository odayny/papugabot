package com.odayny.telegram.papugabot.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.odayny.telegram.papugabot.model.GoogleApiKeys;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PapugabotConfig {
    @Value("${bot.api.key}")
    private String botApiKey;
    @Value("${google.cse.cx}")
    private String googleCx;
    @Value("${google.api.key}")
    private String googleKey;


    @Bean
    public TelegramBot telegramBot() {
        return TelegramBotAdapter.build(botApiKey);
    }

    @Bean
    public Customsearch customsearch() {
        return new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
    }

    @Bean
    public GoogleApiKeys googleApiKeys() {
        return new GoogleApiKeys(googleKey, googleCx);
    }
}
