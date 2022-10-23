package main.api.factories;

import main.api.db.tables.MealItems;
import main.api.domain.MealItem;
import main.api.domain.MealItemType;
import main.api.domain.MealRules;

import java.util.*;

/**
 * Lunch factory!
 */
public class LunchFactory extends StandardMealFactoryImpl {

    /**
     * Constructor for the standard implementation of meal factory.
     *
     * @param mealItems the meal items db
     */
    public LunchFactory(MealItems mealItems) {
        super(mealItems(mealItems), mealRules(mealItems));
    }

    private static Collection<MealItem> mealItems(MealItems mealItems) {
        List<MealItem> lunchItems = new ArrayList<>();
        lunchItems.add(mealItems.get("Sandwich"));
        lunchItems.add(mealItems.get("Chips"));
        lunchItems.add(mealItems.get("Soda"));
        return lunchItems;
    }

    private static MealRules mealRules(MealItems mealItems) {
        // Lunch required types: Main, Side
        Set<MealItemType> lunchRequiredTypeSet = new HashSet<>();
        lunchRequiredTypeSet.add(MealItemType.MAIN);
        lunchRequiredTypeSet.add(MealItemType.SIDE);

        // Lunch items that can be multiple: none
        Set<MealItem> lunchItemsThatCanBeMultiple = new HashSet<>();

        // Lunch item types that can be multiple: Side
        Set<MealItemType> lunchItemTypesThatCanBeMultiple = new HashSet<>();
        lunchItemTypesThatCanBeMultiple.add(MealItemType.SIDE);

        // Lunch defaults: Water if no Drink is ordered
        Map<MealItemType, MealItem> lunchDefaultMap = new HashMap<>();
        lunchDefaultMap.put(MealItemType.DRINK, mealItems.get("Water"));

        // Lunch to add if present: none
        Set<MealItem> lunchAddedIfAbsent = new HashSet<>();

        return new MealRules(lunchRequiredTypeSet, lunchItemsThatCanBeMultiple,
                lunchItemTypesThatCanBeMultiple, lunchDefaultMap, lunchAddedIfAbsent);
    }

}
