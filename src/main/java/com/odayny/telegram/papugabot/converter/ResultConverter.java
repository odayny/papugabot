package com.odayny.telegram.papugabot.converter;

import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.util.List;

public interface ResultConverter {
    InlineQueryResult[] convertResults(List<String> searchResult);
}
