package RUPizza;

/**
 * Represents a factory for creating different types of Chicago-style pizzas
 * with predefined crusts for each pizza type.
 * Implements the {@link PizzaFactory} interface.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates a Chicago-style Deluxe pizza with a Deep Dish crust.
     *
     * @param size the size of the Deluxe pizza
     * @return a new {@link Deluxe} pizza with Deep Dish crust
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.DEEP_DISH, size);
    }

    /**
     * Creates a Chicago-style BBQ Chicken pizza with a Pan crust.
     *
     * @param size the size of the BBQ Chicken pizza
     * @return a new {@link BBQChicken} pizza with Pan crust
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.PAN, size);
    }

    /**
     * Creates a Chicago-style Meatzza pizza with a Stuffed crust.
     *
     * @param size the size of the Meatzza pizza
     * @return a new {@link Meatzza} pizza with Stuffed crust
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.STUFFED, size);
    }

    /**
     * Creates a Chicago-style Build Your Own pizza with a Pan crust.
     *
     * @param size the size of the Build Your Own pizza
     * @return a new {@link BuildYourOwn} pizza with Pan crust
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.PAN, size);
    }
}
