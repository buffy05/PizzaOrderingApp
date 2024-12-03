package com.example.project5;

import android.app.Application;

import RUPizza.*;

public class AppContext extends Application {
    private static OrderHistory orderHistory;
    private static Order currentOrder;

    /**
     * Initializes the global application context with a new current order
     * and an order history.
     */
    public static void initialize() {
        if (currentOrder == null) {
            currentOrder = new Order();
        }
        if (orderHistory == null) {
            orderHistory = OrderHistory.getInstance();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        orderHistory = new OrderHistory();
    }

    /**
     * Returns the order history instance.
     *
     * @return the order history
     */
    public static OrderHistory getOrderHistory() {
        if (orderHistory == null) {
            initialize();
        }
        return orderHistory;
    }

    /**
     * Returns the current order instance.
     *
     * @return the current order
     */
    public static Order getCurrentOrder() {
        if (currentOrder == null) {
            currentOrder = new Order();
        }
        return currentOrder;
    }

    /**
     * Resets the current order to a new order instance.
     */
    public void resetCurrentOrder() {
        currentOrder = new Order();
    }
}
