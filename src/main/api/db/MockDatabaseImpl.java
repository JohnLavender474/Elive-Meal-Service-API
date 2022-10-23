package main.api.db;

import main.api.db.tables.Admins;
import main.api.db.tables.DatabaseTable;
import main.api.db.tables.MealItems;
import main.api.domain.MealItem;
import main.api.domain.MealItemType;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock database.
 */
public class MockDatabaseImpl implements Database {

    private final Map<String, DatabaseTable<?>> databaseTables = new HashMap<>();

    public MockDatabaseImpl() {

        // TODO: Do not hard code tables like this in future versions

        MealItems mealItems = new MealItems();
        // Breakfast
        mealItems.add("Eggs", new MealItem(1, "Eggs", MealItemType.MAIN));
        mealItems.add("Toast", new MealItem(2, "Toast", MealItemType.SIDE));
        mealItems.add("Coffee", new MealItem(3, "Coffee", MealItemType.DRINK));
        // Lunch
        mealItems.add("Sandwich", new MealItem(1, "Sandwich", MealItemType.MAIN));
        mealItems.add("Chips", new MealItem(2, "Chips", MealItemType.SIDE));
        mealItems.add("Soda", new MealItem(3, "Soda", MealItemType.DRINK));
        // Dinner
        mealItems.add("Steak", new MealItem(1, "Steak", MealItemType.MAIN));
        mealItems.add("Potatoes", new MealItem(2, "Potatoes", MealItemType.SIDE));
        mealItems.add("Wine", new MealItem(3, "Wine", MealItemType.DRINK));
        mealItems.add("Cake", new MealItem(4, "Cake", MealItemType.DESSERT));
        // Any
        mealItems.add("Water", new MealItem("Water", MealItemType.DRINK));

        databaseTables.put("MealItems", mealItems);


        Admins admins = new Admins();
        admins.add("user", "password");

        databaseTables.put("Admins", admins);

    }

    /**
     * Gets the database table mapped to the key.
     *
     * @param key the key
     * @return the database table
     */
    @Override
    public DatabaseTable<?> getDatabaseTable(String key) {
        return databaseTables.get(key);
    }

}
