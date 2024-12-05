package RUPizza;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the history of all completed orders in the pizzeria.
 * Provides functionality to add, remove, and retrieve orders from the history.
 * This class implements the Singleton Design Pattern to manage the order history.
 *
 * @author Rhemie Patiak
 * @author Syona Bhandari
 */
public final class OrderHistory {
    /** List of all completed orders in the history. */
    private List<Order> orders;
    private Order currentOrder;
    private static OrderHistory instance;

    /**
     * Constructs a new, empty order history.
     */
    public OrderHistory() {
        orders = new ArrayList<>();
        currentOrder = new Order(1, new ArrayList<>()); // ensures order number starts at 1
    }

    /**
     * Provides a singleton instance of the `OrderHistory` class.
     *
     * @return the singleton instance of `OrderHistory`
     */
    public static synchronized OrderHistory getInstance() {
        if (instance == null) {
            instance = new OrderHistory();
        }
        return instance;
    }

    /**
     * Gets the currently active order.
     *
     * @return the current order
     */
    public Order getCurrentOrder() {
        if (currentOrder == null) {
            resetCurrentOrder();  // reset if null
        }
        Log.d("OrderHistory", "getCurrentOrder called. Current Order: " + currentOrder);
        return currentOrder;
    }

    /**
     * Resets the current order to a new empty order with an incremented order number.
     * If no previous orders exist, the order number starts at 1.
     */
    public void resetCurrentOrder() {
        Log.d("OrderHistory", "Resetting current order.");
        int nextOrderNumber = orders == null || orders.isEmpty() ? 1 : orders.get(orders.size() - 1).getOrderNumber() + 1;
        currentOrder = new Order(nextOrderNumber, new ArrayList<>());
    }

    /**
     * Adds a completed order to the order history.
     *
     * @param order the order to be added to the history.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an order from the order history.
     *
     * @param order the order to be removed from the history,
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Retrieves all orders in the order history.
     *
     * @return a list of all orders in the history.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Finds an order in the history by its unique order number.
     *
     * @param number the unique order number to search for.
     * @return the {@link Order} with the specified order number, or
     * {@code null} if no matching order is found.
     */
    public Order getOrderByNumber(int number) {
        for (Order order : orders) {
            if (order.getOrderNumber() == number) {
                return order;
            }
        }
        return null;   // returns null if there's no matching order found
    }
}
