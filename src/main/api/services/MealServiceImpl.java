package main.api.services;

import main.api.db.Database;
import main.api.db.tables.MealItems;
import main.api.domain.*;
import main.api.exceptions.InvalidInputException;
import main.api.exceptions.InvalidStateException;
import main.api.factories.MealFactory;
import main.api.factories.StandardMealFactoryImpl;

import java.util.*;

/**
 * Implementation of interface {@link MealService}.
 */
public class MealServiceImpl implements MealService {

    private final Map<MealType, MealFactory> mealFactories = new HashMap<>();

    /**
     * Instantiates a new meal service impl.
     */
    public MealServiceImpl(Database database) {

        // In a real world app, the factory classes would have access to the database table for meal items.
        // Instead, we will create static instances that the factories rely on. In a future version, we
        // would rewrite the factory classes to take in the database table(s) as parameter(s).

        MealItems mealItems = (MealItems) database.getDatabaseTable("MealItems");

        // Define breakfast factory:

        // Breakfast items: Eggs, Toast, Coffee
        List<MealItem> breakfastItems = new ArrayList<>();
        breakfastItems.add(mealItems.get("Eggs"));
        breakfastItems.add(mealItems.get("Toast"));
        breakfastItems.add(mealItems.get("Coffee"));

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

        MealRules breakfastRules = new MealRules(breakfastRequiredTypeSet, breakfastItemsThatCanBeMultiple,
                breakfastItemTypesThatCanBeMultiple, breakfastDefaultMap, breakfastAddedIfAbsent);

        MealFactory breakfastFactory = new StandardMealFactoryImpl(breakfastItems, breakfastRules);
        mealFactories.put(MealType.BREAKFAST, breakfastFactory);


        // Define lunch factory:

        // Lunch items: Sandwich, Chips, Soda
        List<MealItem> lunchItems = new ArrayList<>();
        lunchItems.add(mealItems.get("Sandwich"));
        lunchItems.add(mealItems.get("Chips"));
        lunchItems.add(mealItems.get("Soda"));

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

        MealRules lunchRules = new MealRules(lunchRequiredTypeSet, lunchItemsThatCanBeMultiple,
                lunchItemTypesThatCanBeMultiple, lunchDefaultMap, lunchAddedIfAbsent);

        MealFactory lunchFactory = new StandardMealFactoryImpl(lunchItems, lunchRules);
        mealFactories.put(MealType.LUNCH, lunchFactory);


        // Define dinner factory:

        // Dinner items: Steak, Potatoes, Wine, Cake
        List<MealItem> dinnerItems = new ArrayList<>();
        dinnerItems.add(mealItems.get("Steak"));
        dinnerItems.add(mealItems.get("Potatoes"));
        dinnerItems.add(mealItems.get("Wine"));
        dinnerItems.add(mealItems.get("Cake"));

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

        MealRules dinnerRules = new MealRules(dinnerRequiredTypeSet, dinnerItemsThatCanBeMultiple,
                dinnerItemTypesThatCanBeMultiple, dinnerDefaultMap, dinnerAddedIfAbsent);

        MealFactory dinnerFactory = new StandardMealFactoryImpl(dinnerItems, dinnerRules);
        mealFactories.put(MealType.DINNER, dinnerFactory);

    }

    @Override
    public MealOrder convertInputToOrder(String input) {
        // Split the input string by whitespace
        String[] parseTypeAndItems = input.split("\\s+");
        // There should only be two tokens
        if (parseTypeAndItems.length > 2 || parseTypeAndItems.length < 1) {
            throw new InvalidInputException("Invalid parsing, " +
                    "input must be in format \"<meal type> <item1>,<item2>,<item3>...\"");
        }
        // Attempt to retrieve the meal type value from the first token
        MealType type;
        try {
            type = MealType.valueOf(parseTypeAndItems[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("\"Unsupported meal type option: \" + parseTypeAndItems[0]");
        }
        // If there is no second token, then go ahead and return a meal order with empty item ids list
        if (parseTypeAndItems.length != 2) {
            return new MealOrder(type, new ArrayList<>());
        }
        // Split the second token by its commas
        String[] itemsParsed = parseTypeAndItems[1].split(",");
        List<Integer> itemIds = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        // Each token in the items parsed array should be an integer value
        for (String itemParsed : itemsParsed) {
            try {
                int itemId = Integer.parseInt(itemParsed);
                itemIds.add(itemId);
            } catch (NumberFormatException e) {
                errors.add("Invalid item id entry: " + itemParsed);
            }
        }
        // If there are errors, then we cannot convert the input into a meal order object
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
        return new MealOrder(type, itemIds);
    }

    @Override
    public Meal orderMeal(MealOrder order) {
        MealFactory mealFactory = mealFactories.get(order.getType());
        if (mealFactory == null) {
            throw new InvalidStateException("No meal factory mapped to meal type: " + order.getType());
        }
        return mealFactory.createMeal(order);
    }

    @Override
    public void addMealOption(MealType mealType, String input) {

        // In a real app, the add meal option method would add an entry to the database table

        throw new UnsupportedOperationException("Unsupported operation: add meal option");
    }

    @Override
    public void removeMealOption(MealType type, int id) {

        // In a real app, the remove meal option method would remove an entry to the database table

        throw new UnsupportedOperationException("Unsupported operation: remove meal option");
    }

}
