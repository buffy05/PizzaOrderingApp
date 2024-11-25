package RUPizza;

/**
 * Represents a customizable pizza where users can choose their own toppings.
 * Pricing is based on size and the number of added toppings.
 * Extends the {@link Pizza} class.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class BuildYourOwn extends Pizza {

    /**
     * Initializes a Build Your Own pizza with the specified crust and size.
     *
     * @param crust the crust type for the Build Your Own pizza
     * @param size  the size of the Build Your Own pizza
     */
    public BuildYourOwn(Crust crust, Size size) {
        super(crust, size);
    }

    /**
     * Returns the price of the pizza based on its size and number of toppings.
     * Base price per size: $8.99 for SMALL, $10.99 for MEDIUM, $12.99 for LARGE.
     * Each additional topping adds $1.69.
     *
     * @return the total price as a double
     */
    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL: basePrice = 8.99; break;
            case MEDIUM: basePrice = 10.99; break;
            case LARGE: basePrice = 12.99; break;
            default: basePrice = 0;
        }
        return basePrice + (getToppings().size() * 1.69);
    }
}
