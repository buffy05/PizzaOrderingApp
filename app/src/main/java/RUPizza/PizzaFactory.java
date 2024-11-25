package RUPizza;

/**
 * This interface defines the contract for creating different types of pizzas.
 * It provides methods for creating specific pizza types, such as Deluxe, BBQChicken, Meatzza,
 * and BuildYourOwn, each with a specified size.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public interface PizzaFactory {

    /**
     * Creates a Deluxe pizza of the specified size.
     *
     * @param size the size of the pizza to create
     * @return a Deluxe pizza of the specified size
     */
    Pizza createDeluxe(Size size);

    /**
     * Creates a BBQChicken pizza of the specified size.
     *
     * @param size the size of the pizza to create
     * @return a BBQChicken pizza of the specified size
     */
    Pizza createBBQChicken(Size size);

    /**
     * Creates a Meatzza pizza of the specified size.
     *
     * @param size the size of the pizza to create
     * @return a Meatzza pizza of the specified size
     */
    Pizza createMeatzza(Size size);

    /**
     * Creates a BuildYourOwn pizza of the specified size.
     *
     * @param size the size of the pizza to create
     * @return a BuildYourOwn pizza of the specified size
     */
    Pizza createBuildYourOwn(Size size);
}
