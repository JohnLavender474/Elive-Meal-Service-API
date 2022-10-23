package main.api.factories;

import main.api.db.tables.MealItems;
import main.api.domain.MealItem;
import main.api.domain.MealItemType;
import main.api.domain.MealRules;

import java.util.*;

/**
 * Dinner factory!
 */
public class DinnerFactory extends StandardMealFactoryImpl {

    /**
     * Constructor for the standard implementation of meal factory.
     *
     * @param mealItems the meal items db
     */
    public DinnerFactory(MealItems mealItems) {
        super(mealItems(mealItems), mealRules(mealItems));
    }

    private static Collection<MealItem> mealItems(MealItems mealItems) {
        List<MealItem> dinnerItems = new ArrayList<>();
        dinnerItems.add(mealItems.get("Steak"));
        dinnerItems.add(mealItems.get("Potatoes"));
        dinnerItems.add(mealItems.get("Wine"));
        dinnerItems.add(mealItems.get("Cake"));
        return dinnerItems;
    }

    private static MealRules mealRules(MealItems mealItems) {
        // Dinner required types: Main, Side, Dessert
        Set<MealItemType> dinnerRequiredTypeSet = new HashSet<>();
        dinnerRequiredTypeSet.add(MealItemType.MAIN);
        dinnerRequiredTypeSet.add(MealItemType.SIDE);
        dinnerRequiredTypeSet.add(MealItemType.DESSERT);

        // Dinner items that can be multiple: none
        Set<MealItem> dinnerItemsThatCanBeMultiple = new HashSet<>();

        // Dinner items types that can be multiple: none
        Set<MealItemType> dinnerItemTypesThatCanBeMultiple = new HashSet<>();

        // Dinner default map: Water if no Drink is ordered
        Map<MealItemType, MealItem> dinnerDefaultMap = new HashMap<>();
        dinnerDefaultMap.put(MealItemType.DRINK, mealItems.get("Water"));

        // Dinner add if absent: Add Water if not present
        Set<MealItem> dinnerAddedIfAbsent = new HashSet<>();
        dinnerAddedIfAbsent.add(mealItems.get("Water"));

        return new MealRules(dinnerRequiredTypeSet, dinnerItemsThatCanBeMultiple,
                dinnerItemTypesThatCanBeMultiple, dinnerDefaultMap, dinnerAddedIfAbsent);
    }

}
