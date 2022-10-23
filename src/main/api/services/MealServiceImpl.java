package main.api.services;

import main.api.db.Database;
import main.api.db.tables.MealItems;
import main.api.domain.*;
import main.api.exceptions.InvalidInputException;
import main.api.exceptions.InvalidStateException;
import main.api.factories.*;

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
        MealItems mealItems = (MealItems) database.getDatabaseTable("MealItems");
        mealFactories.put(MealType.BREAKFAST, new BreakfastFactory(mealItems));
        mealFactories.put(MealType.LUNCH, new LunchFactory(mealItems));
        mealFactories.put(MealType.DINNER, new DinnerFactory(mealItems));
    }

    @Override
    public MealOrder convertInputToOrder(String input) {
        int i = 0;
        while (i < input.length() && Character.isWhitespace(input.charAt(i))) {
            i++;
        }
        String fixedInput = input.substring(i);
        // Split the input string by whitespace
        String[] parseTypeAndItems = fixedInput.split("\\s+");
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
