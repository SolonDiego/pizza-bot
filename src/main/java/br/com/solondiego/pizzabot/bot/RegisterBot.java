package br.com.solondiego.pizzabot.bot;

import java.io.*;

public class RegisterBot {

    public String bot_token;
    public String bot_user_name;

    public RegisterBot(String uri){

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(uri), "UTF-8"));

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] keys = linha.trim().split(";");
                this.bot_token= keys[0];
                this.bot_user_name = keys[1];
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getBotToken() {
        return bot_token;
    }

    public String getBotUserName() {
        return bot_user_name;
    }
}
