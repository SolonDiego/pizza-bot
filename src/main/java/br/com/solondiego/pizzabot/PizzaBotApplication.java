package br.com.solondiego.pizzabot;

import br.com.solondiego.pizzabot.bot.MongoDB;
import br.com.solondiego.pizzabot.bot.Responder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class PizzaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaBotApplication.class, args);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new Responder());

			MongoDB.connectToDatabase();

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
