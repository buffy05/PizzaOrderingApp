package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a Deluxe pizza with a set of default toppings and size-based pricing.
 * Extends the {@link Pizza} class.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Deluxe extends Pizza {

    /**
     * Initializes a Deluxe pizza with the specified crust and size,
     * adding default toppings: Sausage, Pepperoni, Green Pepper, Onion, and Mushroom.
     *
     * @param crust the crust type for the Deluxe pizza
     * @param size  the size of the Deluxe pizza
     */
    public Deluxe(Crust crust, Size size) {
        super(crust, size);
        addTopping(Topping.SAUSAGE);
        addTopping(Topping.PEPPERONI);
        addTopping(Topping.GREEN_PEPPER);
        addTopping(Topping.ONION);
        addTopping(Topping.MUSHROOM);
    }

    /**
     * Returns the price of the Deluxe pizza based on its size.
     *
     * @return the price as a double: $16.99 for SMALL, $18.99 for MEDIUM, $20.99 for LARGE
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 16.99;
            case MEDIUM: return 18.99;
            case LARGE: return 20.99;
            default: return 0;
        }
    }

    public static final Creator<Deluxe> CREATOR = new Creator<Deluxe>() {
        @Override
        public Deluxe createFromParcel(Parcel in) {
            return new Deluxe(in);
        }

        @Override
        public Deluxe[] newArray(int size) {
            return new Deluxe[size];
        }
    };

    protected Deluxe(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
