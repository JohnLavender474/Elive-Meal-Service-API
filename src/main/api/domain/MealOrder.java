package main.api.domain;

import main.api.exceptions.InvalidInputException;
import main.api.utils.ConversionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Creates a new meal order consisting of a meal type along with the ids of the items being ordered.
 */
public class MealOrder {

    private final MealType type;
    private final List<Integer> itemIds;

    /**
     * Instantiates a new meal order object.
     *
     * @param type the meal type
     * @param itemIds the meal item ids
     */
    public MealOrder(MealType type, List<Integer> itemIds) {
        if (type == null || itemIds == null) {
            throw new InvalidInputException("Args for Meal Order cannot be null");
        }
        this.type = type;
        this.itemIds = itemIds;
        Collections.sort(itemIds);
    }

    /**
     * Gets the meal type.
     *
     * @return the meal type
     */
    public MealType getType() {
        return type;
    }

    /**
     * Gets an unmodifiable view of the meal item ids.
     *
     * @return the meal item ids
     */
    public List<Integer> getItemIds() {
        return Collections.unmodifiableList(itemIds);
    }

    @Override
    public int hashCode() {
        int hash = 49;
        hash += 7 * type.hashCode();
        hash += 7 * itemIds.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MealOrder)) {
            return false;
        }
        MealOrder m = (MealOrder) o;
        return type == m.getType() && itemIds.equals(m.getItemIds());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConversionUtils.enumToProperStr(type)).append(" ");
        for (int i = 0; i < itemIds.size(); i++) {
            sb.append(itemIds.get(i));
            if (i < itemIds.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
