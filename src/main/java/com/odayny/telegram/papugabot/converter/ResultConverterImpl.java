package com.odayny.telegram.papugabot.converter;

import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ResultConverterImpl implements ResultConverter {
    @Override
    public InlineQueryResult[] convertResults(List<String> searchResult) {
        return searchResult.stream().map(s -> new InlineQueryResultPhoto(UUID.randomUUID().toString(), s, s)).collect(Collectors.toList()).toArray(new InlineQueryResult[]{});
    }
}
