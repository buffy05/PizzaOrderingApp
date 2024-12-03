package com.example.project5;

import RUPizza.*;

public class PizzaObject {
    private String pizzaName;
    private Pizza pizza;
    private int imageResId;
    private boolean isSelected;

    public PizzaObject(String pizzaName, Pizza pizza, int imageResId) {
        this.pizzaName = pizzaName;
        this.pizza = pizza;
        this.imageResId = imageResId;
        this.isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
