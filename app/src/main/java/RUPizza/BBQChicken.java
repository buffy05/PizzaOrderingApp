package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a BBQ Chicken pizza with a set of default toppings and size-based pricing.
 * Extends the {@link Pizza} class.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class BBQChicken extends Pizza {

    /**
     * Initializes a BBQ Chicken pizza with the specified crust and size,
     * adding default toppings: BBQ Chicken, Green Pepper, Provolone, and Cheddar.
     *
     * @param crust the crust type for the BBQ Chicken pizza
     * @param size  the size of the BBQ Chicken pizza
     */
    public BBQChicken(Crust crust, Size size) {
        super(crust, size);
        addTopping(Topping.BBQ_CHICKEN);
        addTopping(Topping.GREEN_PEPPER);
        addTopping(Topping.PROVOLONE);
        addTopping(Topping.CHEDDAR);
    }

    /**
     * Returns the price of the BBQ Chicken pizza based on its size.
     *
     * @return the price as a double: $14.99 for SMALL, $16.99 for MEDIUM, $19.99 for LARGE
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 14.99;
            case MEDIUM: return 16.99;
            case LARGE: return 19.99;
            default: return 0;
        }
    }

    protected BBQChicken(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator<BBQChicken> CREATOR = new Creator<BBQChicken>() {
        @Override
        public BBQChicken createFromParcel(Parcel in) {
            return new BBQChicken(in);
        }

        @Override
        public BBQChicken[] newArray(int size) {
            return new BBQChicken[size];
        }
    };
}
