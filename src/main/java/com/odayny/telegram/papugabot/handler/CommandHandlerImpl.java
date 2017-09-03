package com.odayny.telegram.papugabot.handler;

import com.odayny.telegram.papugabot.model.db.WhitelistItem;
import com.odayny.telegram.papugabot.repository.WhitelistItemRepo;
import com.odayny.telegram.papugabot.validator.GodValidator;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandHandlerImpl implements CommandHandler {
    private static final String ADDED_CONFIRMATION = "User %s whitelisted.";
    private static final String REMOVED_CONFIRMATION = "User %s removed from whitelist.";
    private static final String WELCOME_MESSAGE = "Hello. I can search images in Google for you. Due to Google API limitations not anyone can use me. So first of all call /myid and send result to @odayny. He may add you to whitelist... or not.";
    private static final String CONGRATULATION_MESSAGE = "You can use Google images search from now.";
    private static final String RM_NOTIFICATION = "You cannot use Google images search anymore.";
    private static final String UNKNOWN_COMMAND = "Have no idea what you're talking about %s.";
    private TelegramBot bot;
    private GodValidator godValidator;
    private WhitelistItemRepo whitelistItemRepo;

    @Autowired
    public CommandHandlerImpl(TelegramBot bot, GodValidator godValidator, WhitelistItemRepo whitelistItemRepo) {
        this.bot = bot;
        this.godValidator = godValidator;
        this.whitelistItemRepo = whitelistItemRepo;
    }


    @Override
    public void executeCommand(Update update) {
        if (StringUtils.startsWith(update.message().text(), "/start")) {
            welcome(update);
            return;
        }
        if (StringUtils.startsWith(update.message().text(), "/myid")) {
            returnId(update);
            return;
        }
        if (StringUtils.startsWith(update.message().text(), "/adduser")) {
            adduser(update);
            return;
        }
        if (StringUtils.startsWith(update.message().text(), "/rmuser")) {
            rmuser(update);
            return;
        }
        unknownCommand(update);
    }

    private void unknownCommand(Update update) {
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), String.format(UNKNOWN_COMMAND, update.message().text()));
        bot.execute(sendMessage);
    }

    private void rmuser(Update update) {
        godValidator.validate(update);
        String[] split = StringUtils.split(update.message().text());
        String userId = StringUtils.trimToEmpty(split[1]);
        whitelistItemRepo.deleteByUserId(userId);
        confirmRm(update, userId);
        sendRmNotification(userId);
    }

    private void sendRmNotification(String userId) {
        SendMessage sendMessage = new SendMessage(userId, RM_NOTIFICATION);
        bot.execute(sendMessage);
    }

    private void confirmRm(Update update, String userId) {
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), String.format(REMOVED_CONFIRMATION, userId));
        bot.execute(sendMessage);
    }

    private void adduser(Update update) {
        godValidator.validate(update);
        String[] split = StringUtils.split(update.message().text());
        WhitelistItem whitelistItem = new WhitelistItem();
        whitelistItem.setUserId(StringUtils.trimToEmpty(split[1]));
        whitelistItemRepo.save(whitelistItem);
        confirmAdd(update, whitelistItem.getUserId());
        congratulate(whitelistItem.getUserId());
    }

    private void confirmAdd(Update update, String userId) {
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), String.format(ADDED_CONFIRMATION, userId));
        bot.execute(sendMessage);
    }

    private void congratulate(String userId) {
        SendMessage sendMessage = new SendMessage(userId, CONGRATULATION_MESSAGE);
        bot.execute(sendMessage);
    }

    private void welcome(Update update) {
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), WELCOME_MESSAGE);
        bot.execute(sendMessage);
    }

    private void returnId(Update update) {
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), update.message().from().id().toString());
        bot.execute(sendMessage);
    }
}
