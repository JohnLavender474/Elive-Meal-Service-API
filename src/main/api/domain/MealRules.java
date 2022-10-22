package main.api.domain;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the rules that must be adhered to in a menu selection.
 */
public class MealRules {

    private final Set<MealItemType> requiredTypeSet;
    private final Set<MealItem> setOfItemsThatCanBeMultiple;
    private final Set<MealItemType> setOfTypesThatCanBeMultiple;
    private final Map<MealItemType, MealItem> defaultMap;

    /**
     * Instantiates a new set of menu rules.
     *
     * @param requiredTypeSet             the set of meal item types that require user selection
     * @param setOfItemsThatCanBeMultiple the set of meal items that the user is allowed to order more than one of
     * @param setOfTypesThatCanBeMultiple the types of items that the user is allowed to order more than one of
     * @param defaultMap                  the map that declares if a meal item type has a default meal item for when
     *                                    the user provides no selection
     */
    MealRules(Set<MealItemType> requiredTypeSet, Set<MealItem> setOfItemsThatCanBeMultiple,
              Set<MealItemType> setOfTypesThatCanBeMultiple, Map<MealItemType, MealItem> defaultMap) {
        this.requiredTypeSet = requiredTypeSet;
        this.setOfItemsThatCanBeMultiple = setOfItemsThatCanBeMultiple;
        this.setOfTypesThatCanBeMultiple = setOfTypesThatCanBeMultiple;
        this.defaultMap = defaultMap;
    }

    /**
     * Returns if the meal item type requires user selection.
     *
     * @param type the meal item type
     * @return if the type requires user selection
     */
    public boolean isRequired(MealItemType type) {
        return requiredTypeSet.contains(type);
    }

    /**
     * Returns if there is a default item for the meal item type.
     *
     * @param type the meal item type
     * @return if there is a default item for the type
     */
    public Optional<MealItem> getDefault(MealItemType type) {
        return Optional.ofNullable(defaultMap.get(type));
    }

    /**
     * Returns if the user can select more than one of the meal item.
     *
     * @param item the meal item
     * @return if the user can select more than one of the item
     */
    public boolean canBeMultiple(MealItem item) {
        return setOfItemsThatCanBeMultiple.contains(item);
    }

    /**
     * Returns if the user can select more than one of the meal item type.
     *
     * @param type the meal item type
     * @return if the user can select more than one of the type
     */
    public boolean canBeMultiple(MealItemType type) {
        return setOfTypesThatCanBeMultiple.contains(type);
    }

}
