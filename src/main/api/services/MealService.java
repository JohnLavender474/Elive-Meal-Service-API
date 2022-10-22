package main.api.services;

import main.api.domain.Meal;
import main.api.domain.MealType;

import java.util.Collection;

/** Interface for meal service. */
public interface MealService {

    /**
     * Validates the meal order input. If there are errors, then the method returns
     * false and the error messages are placed into the provided error collection.
     *
     * @param input  the meal order input
     * @param errors the list in which to place any errors
     * @return if the input is valid
     */
    boolean validateInput(String input, Collection<String> errors);

    /**
     * Reads the meal order input and returns a meal object with values corresponding
     * to those provided in the input.
     *
     * @param input the meal order input
     * @return the meal
     */
    Meal orderMeal(String input);

    /**
     * Adds an option to the menu selection.
     *
     * @param input the input for the new menu selection
     */
    void addMealOption(String input);

    /**
     * Removes an option from the menu selection.
     *
     * @param type the meal type to remove from.
     * @param id the id of the item to remove
     */
    void removeMealOption(MealType type, int id);

}
