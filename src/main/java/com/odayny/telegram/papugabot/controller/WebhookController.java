package com.odayny.telegram.papugabot.controller;

import com.odayny.telegram.papugabot.handler.UpdateHandler;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);
    private UpdateHandler updateHandler;

    @Autowired
    public WebhookController(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void webhook(@RequestBody String rawUpdate) {
        Update update = BotUtils.parseUpdate(rawUpdate);
        LOGGER.debug("Received request {}", update);
        try {
            updateHandler.handleUpdate(update);
        } catch (Exception e) {
            LOGGER.error("Error while searching {}", update, e);
        }
    }
}
