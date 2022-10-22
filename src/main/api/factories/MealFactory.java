package main.api.factories;

import main.api.domain.Meal;

import java.util.Collection;

/** Interface for objects that are responsible for the instantiation of meals. */
public interface MealFactory {

    /**
     * View a string representation of the menu selection.
     *
     * @return the menu selection
     */
    String getMenuSelection();

    /**
     * Validates the meal specification input. If there are any errors in the input,
     * then return false and add the error messages to the provided error collection.
     *
     * @param input the meal specifications input
     * @return if the input is valid
     */
    boolean validateInput(String input, Collection<String> errors);

    /**
     * Creates a meal object from the input. Throws an {@link main.api.exceptions.InvalidInputException}
     * if there are any errors in the input.
     *
     * @param input the meal specifications input
     * @return the meal object
     */
    Meal createMeal(String input);

}
