package com.odayny.telegram.papugabot.handler;

import com.odayny.telegram.papugabot.converter.ResultConverter;
import com.odayny.telegram.papugabot.model.db.Stats;
import com.odayny.telegram.papugabot.repository.StatsRepo;
import com.odayny.telegram.papugabot.service.ImageSearchService;
import com.odayny.telegram.papugabot.validator.PermissionsValidator;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UpdateHandlerImpl implements UpdateHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateHandlerImpl.class);

    private TelegramBot bot;
    private ImageSearchService imageSearchService;
    private CommandHandler commandHandler;
    private ResultConverter resultConverter;
    private StatsRepo statsRepo;
    private PermissionsValidator permissionsValidator;

    @Autowired
    public UpdateHandlerImpl(TelegramBot bot, ImageSearchService imageSearchService, CommandHandler commandHandler, ResultConverter resultConverter, StatsRepo statsRepo, PermissionsValidator permissionsValidator) {
        this.bot = bot;
        this.imageSearchService = imageSearchService;
        this.commandHandler = commandHandler;
        this.resultConverter = resultConverter;
        this.statsRepo = statsRepo;
        this.permissionsValidator = permissionsValidator;
    }

    @Override
    public void handleUpdate(Update update) throws IOException {
        if (command(update)) {
            commandHandler.executeCommand(update);
            return;
        }
        permissionsValidator.validateWithResponse(update);
        validateAction(update);
        validateQueryLength(update);
        List<String> searchResult = imageSearchService.search(StringUtils.trimToEmpty(update.inlineQuery().query()));
        statsRepo.save(new Stats(update.inlineQuery().from().id().toString()));
        bot.execute(new AnswerInlineQuery(update.inlineQuery().id(), resultConverter.convertResults(searchResult)));
    }

    private void validateAction(Update update) {
        if (update.inlineQuery() == null)
            throw new IllegalArgumentException("No inline query.");
    }

    private boolean command(Update update) {
        return update.message() != null && StringUtils.startsWith(update.message().text(), "/");
    }


    private void validateQueryLength(Update update) {
        if (StringUtils.length(StringUtils.trimToEmpty(update.inlineQuery().query())) < 3) {
            throw new IllegalArgumentException("Query must be at least 3 chars long.");
        }
    }
}
