package RUPizza;

/**
 * Represents a factory for creating New York-style pizzas with specific crust types.
 * Implements the {@link PizzaFactory} interface to provide customized pizzas.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with Brooklyn-style crust.
     *
     * @param size the size of the Deluxe pizza
     * @return a Deluxe pizza with Brooklyn-style crust and the specified size
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.BROOKLYN, size);
    }

    /**
     * Creates a BBQ Chicken pizza with Thin crust.
     *
     * @param size the size of the BBQ Chicken pizza
     * @return a BBQ Chicken pizza with Thin crust and the specified size
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.THIN, size);
    }

    /**
     * Creates a Meatzza pizza with Hand-Tossed crust.
     *
     * @param size the size of the Meatzza pizza
     * @return a Meatzza pizza with Hand-Tossed crust and the specified size
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.HAND_TOSSED, size);
    }

    /**
     * Creates a Build Your Own pizza with Hand-Tossed crust.
     *
     * @param size the size of the Build Your Own pizza
     * @return a Build Your Own pizza with Hand-Tossed crust and the specified size
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.HAND_TOSSED, size);
    }
}
