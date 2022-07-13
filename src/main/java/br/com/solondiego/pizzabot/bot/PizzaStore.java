package br.com.solondiego.pizzabot.bot;

import java.util.*;

public class PizzaStore {
    public final static List<String> PIZZA_TYPE_LIST = new ArrayList<>(Arrays.asList("Chicken", "Pepperoni", "Hawaiian"));
    public final static Map<String, Double> PIZZA_SIZES_MAP = new HashMap<>();
    public final static Map<String, Double> DRINKS_MAP = new HashMap<>();

    static {
        PIZZA_SIZES_MAP.put("Pequena", 10.00);
        PIZZA_SIZES_MAP.put("Média", 15.00);
        PIZZA_SIZES_MAP.put("Grande", 20.00);

        DRINKS_MAP.put(Drinks.Water.toString(), 1.00);
        DRINKS_MAP.put(Drinks.Soda.toString(), 5.00);
        DRINKS_MAP.put(Drinks.Coffer.toString(), 2.00);
    }
}
