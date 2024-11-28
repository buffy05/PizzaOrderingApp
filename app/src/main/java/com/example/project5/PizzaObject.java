package com.example.project5;

import RUPizza.*;

public class PizzaObject {
    private String pizzaName;
    private Pizza pizza;
    private int imageResId;

    public PizzaObject(String pizzaName, Pizza pizza, int imageResId) {
        this.pizzaName = pizzaName;
        this.pizza = pizza;
        this.imageResId = imageResId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public int getImageResId() {
        return imageResId;
    }
}
