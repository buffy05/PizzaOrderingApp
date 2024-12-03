package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Represents a pizza with toppings, crust, and size. This is an abstract class
 * extended by specific pizza types. It provides methods for managing toppings,
 * calculating the price, and retrieving pizza details.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public abstract class Pizza implements Parcelable {
    private ArrayList<Topping> toppings = new ArrayList<>();
    private Crust crust;
    private Size size;

    /**
     * Constructor to create a pizza with a specified crust and size.
     *
     * @param crust the crust type of the pizza
     * @param size the size of the pizza
     */
    public Pizza(Crust crust, Size size) {
        this.crust = crust;
        this.size = size;
    }

    /**
     * Returns the crust type of the pizza.
     *
     * @return the crust type
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Returns the size of the pizza.
     *
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**
     * Returns a copy of the list of toppings on the pizza.
     *
     * @return a list of toppings
     */
    public ArrayList<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    /**
     * Sets the size of the pizza.
     *
     * @param size the size to set
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Adds a topping to the pizza. A maximum of 8 toppings are allowed.
     *
     * @param topping the topping to add
     */
    public void addTopping(Topping topping) {
        if (toppings.size() < 7) {
            toppings.add(topping);
        }
    }

    /**
     * Removes a topping from the pizza.
     *
     * @param topping the topping to remove
     */
    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    /**
     * Checks if the pizza is a "Build Your Own" type.
     * This method should be overridden by subclasses if applicable.
     *
     * @return true if the pizza is "Build Your Own", false otherwise.
     */
    public boolean isBuildYourOwn() {
        return false;
    }

    /**
     * Calculates the price of the pizza. This method is abstract and must be implemented
     * by subclasses to calculate the specific price based on size and toppings.
     *
     * @return the price of the pizza
     */
    public abstract double price();

    /**
     * Provides a string representation of the pizza, including its toppings, crust, size, and price.
     *
     * @return a string representation of the pizza details
     */
    @Override
    public String toString() {
        return "Pizza{" +
                "toppings=" + toppings +
                ", crust=" + crust +
                ", size=" + size +
                ", price=" + String.format("%.2f", price()) +
                '}';
    }

    // parcelable implementation
    protected Pizza (Parcel in) {
        crust = Crust.valueOf(in.readString());
        size = Size.valueOf(in.readString());
        toppings = in.createTypedArrayList(Topping.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(crust.name());
        dest.writeString(size.name());
        dest.writeTypedList(toppings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pizza> CREATOR = new Creator<Pizza>() {
        @Override
        public Pizza createFromParcel(Parcel in) {
            String className = in.readString();
            try {
                Class<?> clazz = Class.forName(className);
                return (Pizza) clazz.getConstructor(Parcel.class).newInstance(in);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }
    };
}
