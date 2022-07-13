package br.com.solondiego.pizzabot.bot.Strategy;

import br.com.solondiego.pizzabot.bot.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements Strategy {
    @Override
    public SendMessage getResponse(Update update) {

        String chatId = String.valueOf(update.getMessage().getChatId());

        SendMessage response = new SendMessage();

        response.setChatId(chatId);
        response.setText(CommonMessages.UNKNOWN_RESPONSE);

        String textUpdate = update.getMessage().getText().trim();

        if (textUpdate.equalsIgnoreCase("/start")) {

            if(!MongoDB.userExists(chatId)){
                MongoDB.insertNewUserId(chatId);
            }

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.PIZZA_SELECTION.toString(), chatId);

            response.setText("Bem-vindo ao Pizza Bot. Selecione a opção de pizza que deseja.");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (String pizza : PizzaStore.PIZZA_TYPE_LIST) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(pizza);
                button.setCallbackData(pizza);
                buttonsRow.add(button);
            }

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);

            return response;
        }

        if(MongoDB.getFieldValue(MongoDB.ORDER_STATE, chatId).equalsIgnoreCase(OrderState.ADDRESS.toString())){
            MongoDB.updateField(MongoDB.ADDRESS, textUpdate, chatId);

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.PAYMENT.toString(), chatId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Seu pedido total é de:\n\nR$").append(Calculator.getFinalPrice(chatId)).append("\n\nPara voltar ao menu principal, digite /start");

            response.setText(stringBuilder.toString());
        }

        return response;

    }
}
