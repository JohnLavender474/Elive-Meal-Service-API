package main.api.services;

import main.api.domain.Meal;
import main.api.domain.MealOrder;
import main.api.domain.MealType;

/**
 * Interface for meal service.
 */
public interface MealService {

    /**
     * Converts the string input into a meal order object . Throws an {@link main.api.exceptions.InvalidInputException}
     * if containing a list of errors if the input is invalid.
     *
     * @param input the string input representing the order
     * @return the meal order object
     */
    MealOrder convertInputToOrder(String input);

    /**
     * Reads the meal order and returns a meal object with values corresponding to those provided in the input.
     *
     * @param order the meal order
     * @return the meal
     */
    Meal orderMeal(MealOrder order);

    /**
     * Adds an option to the menu selection.
     *
     * @param input the input for the new menu selection
     */
    void addMealOption(MealType mealType, String input);

    /**
     * Removes an option from the menu selection.
     *
     * @param type the meal type to remove from.
     * @param id   the id of the item to remove
     */
    void removeMealOption(MealType type, int id);

}
