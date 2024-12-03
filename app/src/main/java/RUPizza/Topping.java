package RUPizza;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * The Topping enum represents the available toppings for a pizza.
 * It includes various toppings such as meats, vegetables, and cheeses.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public enum Topping implements Parcelable{
    SELECT_A_TOPPING, SAUSAGE, PEPPERONI, GREEN_PEPPER, ONION, MUSHROOM,
    BBQ_CHICKEN, PROVOLONE, CHEDDAR, BEEF, HAM,
    EXTRA_CHEESE, OLIVES, SPINACH, PINEAPPLE, CHEESE;

    // parcelable implementation
    public static final Creator<Topping> CREATOR = new Creator<Topping>() {
        @Override
        public Topping createFromParcel(Parcel in) {
            return Topping.valueOf(in.readString());
        }

        @Override
        public Topping[] newArray(int size) {
            return new Topping[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name());
    }
}
