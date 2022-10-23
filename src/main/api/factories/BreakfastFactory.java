package main.api.factories;

import main.api.db.tables.MealItems;
import main.api.domain.MealItem;
import main.api.domain.MealItemType;
import main.api.domain.MealRules;

import java.util.*;

/**
 * Breakfast factory!
 */
public class BreakfastFactory extends StandardMealFactoryImpl {

    /**
     * Constructor for the standard implementation of meal factory.
     *
     * @param mealItems the meal items db table
     */
    public BreakfastFactory(MealItems mealItems) {
        super(mealItems(mealItems), mealRules(mealItems));
    }

    private static Collection<MealItem> mealItems(MealItems mealItems) {
        List<MealItem> breakfastItems = new ArrayList<>();
        breakfastItems.add(mealItems.get("Eggs"));
        breakfastItems.add(mealItems.get("Toast"));
        breakfastItems.add(mealItems.get("Coffee"));
        return breakfastItems;
    }

    private static MealRules mealRules(MealItems mealItems) {
        // Breakfast required item types: Main and Side
        Set<MealItemType> breakfastRequiredTypeSet = new HashSet<>();
        breakfastRequiredTypeSet.add(MealItemType.MAIN);
        breakfastRequiredTypeSet.add(MealItemType.SIDE);

        // Breakfast items that can be multiple: Coffee
        Set<MealItem> breakfastItemsThatCanBeMultiple = new HashSet<>();
        breakfastItemsThatCanBeMultiple.add(mealItems.get("Coffee"));

        // Breakfast item types that can be multiple: none
        Set<MealItemType> breakfastItemTypesThatCanBeMultiple = new HashSet<>();

        // Breakfast defaults: add Water if no Drink is ordered
        Map<MealItemType, MealItem> breakfastDefaultMap = new HashMap<>();
        breakfastDefaultMap.put(MealItemType.DRINK, mealItems.get("Water"));

        // Breakfast to add if absent: none
        Set<MealItem> breakfastAddedIfAbsent = new HashSet<>();

        return new MealRules(breakfastRequiredTypeSet, breakfastItemsThatCanBeMultiple,
                breakfastItemTypesThatCanBeMultiple, breakfastDefaultMap, breakfastAddedIfAbsent);
    }

}
