package main.api.domain;

/** Represents a meal item, including the meal item's id, name, and type. */
public class MealItem {

    private final int id;
    private final String name;
    private final MealItemType type;

    /**
     * Instantiates a new meal item.
     *
     * @param id the meal item id
     * @param name the meal item name
     * @param type the meal item type
     */
    public MealItem(int id, String name, MealItemType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the meal item id.
     *
     * @return the meal item id
     */
    public int getId() {
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
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MealItem && id == ((MealItem) o).getId();
    }

}
