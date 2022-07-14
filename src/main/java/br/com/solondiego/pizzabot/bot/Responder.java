package br.com.solondiego.pizzabot.bot;

import br.com.solondiego.pizzabot.bot.Strategy.CallBackStrategy;
import br.com.solondiego.pizzabot.bot.Strategy.TextStrategy;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Responder extends TelegramLongPollingBot {

    Logger LOGGER = Logger.getLogger(Responder.class.getName());

    @Override
    public String getBotUsername() {
        return RegisterBot.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return RegisterBot.BOT_TOKEN;
    }

    @Override
    public synchronized void onUpdateReceived(Update update) {

        try {

            SendMessage response = null;

            if (update.hasCallbackQuery()) {
                response = new CallBackStrategy().getResponse(update);
            }

            if (update.hasMessage()) {
                response = new TextStrategy().getResponse(update);
            }

            if (response == null) {
                LOGGER.log(Level.WARNING, "ERRO: O tipo de atualização não pôde ser determinado.", update);
                return;
            }

            sendApiMethod(response);

        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }

    }

}
