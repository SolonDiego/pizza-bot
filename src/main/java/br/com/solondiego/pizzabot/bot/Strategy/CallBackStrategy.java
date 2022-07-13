package br.com.solondiego.pizzabot.bot.Strategy;

import br.com.solondiego.pizzabot.bot.CommonMessages;
import br.com.solondiego.pizzabot.bot.MongoDB;
import br.com.solondiego.pizzabot.bot.OrderState;
import br.com.solondiego.pizzabot.bot.PizzaStore;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallBackStrategy implements Strategy {
    @Override
    public SendMessage getResponse(Update update) {

        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(CommonMessages.UNKNOWN_RESPONSE);

        String callbackResponse = update.getCallbackQuery().getData();

        if (PizzaStore.PIZZA_TYPE_LIST.contains(callbackResponse)) {

            MongoDB.updateField(MongoDB.PIZZA_TYPE, callbackResponse, chatId);

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.PIZZA_SIZE.toString(), chatId);

            response.setText("Por favor, selecione o tamanho de sua pizza:");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (Map.Entry<String, Double> set : PizzaStore.PIZZA_SIZES_MAP.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(set.getKey()).append(": R$").append(set.getValue());
                button.setText(stringBuilder.toString());
                button.setCallbackData(set.getKey());
                buttonsRow.add(button);
            }

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        if (PizzaStore.PIZZA_SIZES_MAP.containsKey(callbackResponse)) {

            MongoDB.updateField(MongoDB.PIZZA_SIZE, callbackResponse, chatId);

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.DRINKS.toString(), chatId);

            response.setText("Por favor, selecione sua bebida:");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (Map.Entry<String, Double> set : PizzaStore.DRINKS_MAP.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(set.getKey()).append(": R$").append(set.getValue());
                button.setText(stringBuilder.toString());
                button.setCallbackData(set.getKey());
                buttonsRow.add(button);
            }

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        if (PizzaStore.DRINKS_MAP.containsKey(callbackResponse)) {

            MongoDB.updateField(MongoDB.DRINK, callbackResponse, chatId);

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.ADDRESS.toString(), chatId);

            response.setText("Por favor, digite o seu endereço de entrega.");

        }

        return response;
    }
}
