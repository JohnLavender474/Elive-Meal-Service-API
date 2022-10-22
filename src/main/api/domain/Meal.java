package main.api.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Meal {

    private final Map<MealItem, Integer> itemMap = new HashMap<>();

    public Meal(Collection<MealItem> items) {
        items.forEach(item -> itemMap.put(item, item.getId()));
    }

    public Map<MealItem, Integer> getItemMap() {
        return itemMap;
    }

}
