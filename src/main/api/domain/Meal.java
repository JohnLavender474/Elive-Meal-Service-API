package main.api.domain;

import java.util.Map;
import java.util.SortedMap;

/**
 * Contains a map with the meal items and the amount of each that is ordered in the meal. It is required that the meal
 * item map be sorted as per the client requirement that meals be listed in a particular order to the end user.
 */
public class Meal {

    private final SortedMap<MealItem, Integer> itemMap;

    /**
     * Instantiates a new meal using the meal item map where keys are each unique item and the values are the amount
     * of each item.
     *
     * @param itemMap the meal item map
     */
    public Meal(SortedMap<MealItem, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    /**
     * Gets the map containing the meal items as keys and the amount of each as values.
     *
     * @return the map containing the meal items and count of each
     */
    public SortedMap<MealItem, Integer> getItemMap() {
        return itemMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = itemMap.entrySet().size();
        int i = 0;
        for (Map.Entry<MealItem, Integer> entry : itemMap.entrySet()) {
            MealItem item = entry.getKey();
            int count = entry.getValue();
            sb.append(item.getName());
            if (count > 1) {
                sb.append("(").append(count).append(")");
            }
            if (i < size - 1) {
                sb.append(", ");
            }
            i++;
        }
        return sb.toString();
    }

}
