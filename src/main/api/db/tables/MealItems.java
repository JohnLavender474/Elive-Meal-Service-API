package main.api.db.tables;

import main.api.domain.MealItem;

import java.util.*;

/**
 * MockDatabase implementation for storing meal items.
 */
public class MealItems implements DatabaseTable<MealItem> {

    private final Map<String, MealItem> mealItems = new HashMap<>();

    @Override
    public void add(String key, MealItem val) {
        mealItems.put(key, val);
    }

    @Override
    public void remove(String key) {
        mealItems.remove(key);
    }

    @Override
    public MealItem get(String key) {
        return mealItems.get(key);
    }

}
