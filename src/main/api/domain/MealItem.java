package main.api.domain;

import main.api.exceptions.InvalidInputException;

import java.util.Objects;

/**
 * Represents a meal item, including the meal item's id, name, and type.
 */
public class MealItem implements Comparable<MealItem> {

    private final Integer id;
    private final String name;
    private final MealItemType type;

    /**
     * Instantiates new meal item with null id and ANY meal item type.
     *
     * @param name the name of the meal item
     */
    public MealItem(String name, MealItemType type) {
        this(null, name, type);
    }

    /**
     * Instantiates a new meal item.
     *
     * @param id   the meal item id
     * @param name the meal item name
     * @param type the meal item type
     */
    public MealItem(Integer id, String name, MealItemType type) {
        if (name == null) {
            throw new InvalidInputException("Meal item name cannot be null");
        }
        if (type == null) {
            throw new InvalidInputException("Meal item type cannot be null");
        }
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the meal item id.
     *
     * @return the meal item id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the meal item name.
     *
     * @return the meal item name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the meal item type.
     *
     * @return the meal item type
     */
    public MealItemType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 49;
        if (id != null) {
            hash += 7 * id;
        }
        hash += 7 * name.hashCode();
        hash += 7 * type.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MealItem &&
                Objects.equals(id, ((MealItem) o).getId()) &&
                type == ((MealItem) o).getType() &&
                name.equals(((MealItem) o).getName());
    }

    @Override
    public int compareTo(MealItem m) {
        int comp = type.compareTo(m.getType());
        if (comp == 0) {
            if (id == null) {
                comp = 1;
            } else if (m.getId() == null) {
                comp = -1;
            } else {
                comp = Integer.compare(id, m.getId());
            }
        }
        if (comp == 0) {
            comp = name.compareTo(m.getName());
        }
        return comp;
    }

}
