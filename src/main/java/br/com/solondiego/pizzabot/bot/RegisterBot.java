package br.com.solondiego.pizzabot.bot;

public class RegisterBot {

    public static String BOT_USERNAME;
    public static String BOT_TOKEN;

    static {
        BOT_USERNAME = PropertiesReader.getProperty("bot.username");
        BOT_TOKEN = PropertiesReader.getProperty("bot.token");
    }

}
