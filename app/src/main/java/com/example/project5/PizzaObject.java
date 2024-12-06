package com.example.project5;

import RUPizza.*;

/**
 * Represents a wrapper class for pizza items used in the application.
 * This class encapsulates the pizza name, a {@link Pizza} object,
 * an image resource ID, and a selection state.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class PizzaObject {
    private String pizzaName;
    private Pizza pizza;
    private int imageResId;
    private boolean isSelected;

    /**
     * Constructs a new {@code PizzaObject} with the specified details.
     *
     * @param pizzaName  the name of the pizza.
     * @param pizza      the {@link Pizza} object representing the pizza details.
     * @param imageResId the resource ID of the image representing the pizza.
     */
    public PizzaObject(String pizzaName, Pizza pizza, int imageResId) {
        this.pizzaName = pizzaName;
        this.pizza = pizza;
        this.imageResId = imageResId;
        this.isSelected = false;
    }

    // public methods

    /**
     * Gets the name of the pizza.
     *
     * @return the name of the pizza.
     */
    public String getPizzaName() {
        return pizzaName;
    }

    /**
     * Gets the {@link Pizza} object associated with this pizza item.
     *
     * @return the {@link Pizza} object.
     */
    public Pizza getPizza() {
        return pizza;
    }

    /**
     * Gets the resource ID of the image representing the pizza.
     *
     * @return the resource ID of the pizza image.
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * Checks whether the pizza is currently selected.
     *
     * @return true if the pizza is selected, otherwise false.
     */
    public boolean isSelected() {
        return isSelected;
    }
}
