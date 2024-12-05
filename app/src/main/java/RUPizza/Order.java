package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Represents an order that contains a list of pizzas and provides methods for adding/removing pizzas,
 * calculating the total price, sales tax, and order total.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Order implements Parcelable {
    private int number;
    private ArrayList<Pizza> pizzas;

    // constructors

    /**
     * Constructor to create a new order with a unique order number.
     */
    public Order(int number, ArrayList<Pizza> pizzas) {
        this.number = number;
        this.pizzas = pizzas != null ? pizzas : new ArrayList<>();
    }

    /**
     * Constructs an order from a {@link Parcel}.
     *
     * @param in the {@link Parcel} containing the serialized order data
     */
    protected Order(Parcel in) {
        number = in.readInt();
        pizzas = in.createTypedArrayList(Pizza.CREATOR);
    }

    // public methods

    /**
     * Retrieves the unique order number for this order.
     *
     * @return the unique order number.
     */
    public int getOrderNumber() {
        return number;
    }

    /**
     * Adds a pizza to the order.
     *
     * @param pizza the pizza to add to the order
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Retrieves the list of pizzas in the order.
     *
     * @return an ArrayList of pizzas in the order.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Calculates the total price of all pizzas in the order.
     *
     * @return the subtotal of the pizzas in the order
     */
    public double calculateTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }

    /**
     * Calculates the sales tax for the order based on a fixed tax rate of 6.625%.
     *
     * @return the calculated sales tax for the order
     */
    public double calculateSalesTax() {
        return calculateTotal() * 0.06625;
    }

    /**
     * Calculates the total order price, including both the subtotal and sales tax.
     *
     * @return the total order cost (subtotal + sales tax)
     */
    public double calculateOrderTotal() {
        return calculateTotal() + calculateSalesTax();
    }

    /**
     * Provides a string representation of the order, including the order number,
     * a list of pizzas in the order, and the total cost breakdown (subtotal, sales tax, total) for testing purposes.
     *
     * @return a string representation of the order details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(number).append("\n");
        for (Pizza pizza : pizzas) {
            sb.append(pizza.toString()).append("\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("Sales Tax: $").append(String.format("%.2f", calculateSalesTax())).append("\n");
        sb.append("Order Total: $").append(String.format("%.2f", calculateOrderTotal())).append("\n");
        return sb.toString();
    }

    // parcelable implementation

    /**
     * Writes the order data to a {@link Parcel}.
     *
     * @param dest  the {@link Parcel} where the order data should be written
     * @param flags additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeTypedList(pizzas);
    }

    /**
     * Describes the contents of this {@link Parcelable}.
     *
     * @return an integer representing any special contents (always 0 in this case)
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * A {@link Parcelable.Creator} for creating {@link Order} objects from {@link Parcel}s.
     */
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }
        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
