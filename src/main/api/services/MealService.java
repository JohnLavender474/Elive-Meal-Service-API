package main.api.services;

import main.api.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for meal service.
 */
public interface MealService {

    /**
     * Returns a map containing the meal items for the provided meal type.
     *
     * @param mealType the meal type
     * @return map containing meal items
     */
    Map<MealItemType, Set<MealItem>> viewMenuFor(MealType mealType);

    /**
     * Returns the meal rules for the provided meal type.
     *
     * @param mealType the meal type
     * @return the meal rules
     */
    MealRules getMealRulesFor(MealType mealType);

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

}
