package com.odayny.telegram.papugabot.controller;

import com.odayny.telegram.papugabot.handler.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pool")
public class LongPoolController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LongPoolController.class);

    private TelegramBot bot;
    private UpdateHandler updateHandler;
    private int updateId;

    @Autowired
    public LongPoolController(TelegramBot bot, UpdateHandler updateHandler) {
        this.bot = bot;
        this.updateHandler = updateHandler;
    }

    @RequestMapping(value = "/{count}", method = RequestMethod.GET)
    public void pool(@PathVariable("count") int count) {
        GetUpdatesResponse getUpdatesResponse = bot.execute(new GetUpdates().limit(count).offset(updateId + 1).timeout(0));
        List<Update> updates = getUpdatesResponse.updates();
        updates.forEach(u -> {
            LOGGER.debug("Pooled {}.", u);
            try {
                updateHandler.handleUpdate(u);
            } catch (Exception e) {
                LOGGER.error("Error while searching {}", u, e);
            }
            updateId = u.updateId();
        });
    }
}