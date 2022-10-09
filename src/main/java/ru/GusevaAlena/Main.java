package ru.GusevaAlena;

import lombok.SneakyThrows;

import okhttp3.Request;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.FromJSON.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        //ApiContextInitializer.init();
        Bot bot = new Bot();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

    }
}
