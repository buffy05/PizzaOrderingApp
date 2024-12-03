package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a Meatzza pizza with a set of default meat toppings and size-based pricing.
 * Extends the {@link Pizza} class.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Meatzza extends Pizza {

    /**
     * Initializes a Meatzza pizza with the specified crust and size,
     * adding default toppings: Sausage, Pepperoni, Beef, and Ham.
     *
     * @param crust the crust type for the Meatzza pizza
     * @param size  the size of the Meatzza pizza
     */
    public Meatzza(Crust crust, Size size) {
        super(crust, size);
        addTopping(Topping.SAUSAGE);
        addTopping(Topping.PEPPERONI);
        addTopping(Topping.BEEF);
        addTopping(Topping.HAM);
    }

    /**
     * Returns the price of the Meatzza pizza based on its size.
     *
     * @return the price as a double: $17.99 for SMALL, $19.99 for MEDIUM, $21.99 for LARGE
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 17.99;
            case MEDIUM: return 19.99;
            case LARGE: return 21.99;
            default: return 0;
        }
    }

    protected Meatzza(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator<Meatzza> CREATOR = new Creator<Meatzza>() {
        @Override
        public Meatzza createFromParcel(Parcel in) {
            return new Meatzza(in);
        }

        @Override
        public Meatzza[] newArray(int size) {
            return new Meatzza[size];
        }
    };
}
