package com.odayny.telegram.papugabot.validator;

import com.odayny.telegram.papugabot.converter.ResultConverter;
import com.odayny.telegram.papugabot.repository.WhitelistItemRepo;
import com.odayny.telegram.papugabot.service.ImageSearchService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PermissionsValidatorImpl implements PermissionsValidator {
    @Value("${gods.id}")
    private String godId;
    private TelegramBot bot;
    private WhitelistItemRepo whitelistItemRepo;
    private ImageSearchService imageSearchService;
    private ResultConverter resultConverter;
    private InlineQueryResult[] easterEgg = null;

    @Autowired
    public PermissionsValidatorImpl(TelegramBot bot, WhitelistItemRepo whitelistItemRepo, ImageSearchService imageSearchService, ResultConverter resultConverter) {
        this.bot = bot;
        this.whitelistItemRepo = whitelistItemRepo;
        this.imageSearchService = imageSearchService;
        this.resultConverter = resultConverter;
    }


    @Override
    public void validateWithResponse(Update update) {
        String userId = update.inlineQuery().from().id().toString();
        if (StringUtils.equals(godId, userId)) {
            return;
        }
        if (whitelistItemRepo.findFirstByUserId(userId) == null) {
            bot.execute(new AnswerInlineQuery(update.inlineQuery().id(), doEasterEgg()));
            throw new IllegalArgumentException(String.format("This guy %s is not whitelisted.", update.inlineQuery().from().id()));
        }
    }

    private synchronized InlineQueryResult[] doEasterEgg() {
        if (easterEgg == null) {
            List<String> noMeme;
            try {
                noMeme = imageSearchService.search("no meme");
                easterEgg = resultConverter.convertResults(noMeme);
            } catch (IOException e) {
                // who actually cares?
            }
        }
        return easterEgg;
    }
}
