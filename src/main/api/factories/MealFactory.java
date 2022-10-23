package main.api.factories;

import main.api.domain.Meal;
import main.api.domain.MealItem;
import main.api.domain.MealItemType;
import main.api.domain.MealOrder;

import java.util.Map;
import java.util.Set;

/**
 * Interface for objects that are responsible for the instantiation of meals.
 */
public interface MealFactory {

    /**
     * View a string representation of the menu selection.
     *
     * @return the menu selection
     */
    Map<MealItemType, Set<MealItem>> getMenuSelection();

    /**
     * Creates a meal object from the meal order. Throws an {@link main.api.exceptions.InvalidInputException} if there
     * are any errors in the meal order.
     *
     * @param order the meal order
     * @return the meal object
     */
    Meal createMeal(MealOrder order);

}
