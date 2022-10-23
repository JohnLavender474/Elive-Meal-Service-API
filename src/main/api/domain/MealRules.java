package main.api.domain;

import main.api.utils.ConversionUtils;

import java.util.*;

/**
 * Represents the rules that must be adhered to in a menu selection.
 */
public class MealRules {

    private final Set<MealItemType> requiredTypeSet;
    private final Set<MealItem> setOfItemsThatCanBeMultiple;
    private final Set<MealItemType> setOfTypesThatCanBeMultiple;
    private final Map<MealItemType, MealItem> defaultMap;
    private final Set<MealItem> addedIfAbsent;

    /**
     * Instantiates a new set of menu rules.
     *
     * @param requiredTypeSet             the set of meal item types that require user selection
     * @param setOfItemsThatCanBeMultiple the set of meal items that the user is allowed to order more than one of
     * @param setOfTypesThatCanBeMultiple the types of items that the user is allowed to order more than one of
     * @param defaultMap                  the map that declares if a meal item type has a default meal item for when
     *                                    the user provides no selection
     */
    public MealRules(Set<MealItemType> requiredTypeSet, Set<MealItem> setOfItemsThatCanBeMultiple,
                     Set<MealItemType> setOfTypesThatCanBeMultiple, Map<MealItemType, MealItem> defaultMap,
                     Set<MealItem> addedIfAbsent) {
        this.requiredTypeSet = requiredTypeSet;
        this.setOfItemsThatCanBeMultiple = setOfItemsThatCanBeMultiple;
        this.setOfTypesThatCanBeMultiple = setOfTypesThatCanBeMultiple;
        this.defaultMap = defaultMap;
        this.addedIfAbsent = addedIfAbsent;
    }

    /**
     * Returns if all the required meal types are satisfied. If false, then the provided set for required types missing
     * is filled with the missing types.
     *
     * @param typesInMeal the types in the meal
     * @param requiredTypesMissingInMeal the set that is filled with the missing types if this method returns false
     * @return if the all the required meal types are satisfied
     */
    public boolean satisfiesRequiredTypes(Set<MealItemType> typesInMeal,
                                          Set<MealItemType> requiredTypesMissingInMeal) {
        Set<MealItemType> temp = EnumSet.copyOf(requiredTypeSet);
        temp.removeAll(typesInMeal);
        if (!temp.isEmpty()) {
            requiredTypesMissingInMeal.addAll(temp);
            return false;
        }
        return true;
    }

    /**
     * Returns an unmodifiable view of the item types that have default values.
     *
     * @return the view of item types with default values
     */
    public Set<MealItemType> getItemTypesWithDefaultVals() {
        return Collections.unmodifiableSet(defaultMap.keySet());
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

    /**
     * Returns unmodifiable view of the items to be added if absent.
     *
     * @return the items to be added if absent
     */
    public Set<MealItem> getItemsToBeAddedIfAbsent() {
        return Collections.unmodifiableSet(addedIfAbsent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. Meal item types that require user selection:\n");
        if (requiredTypeSet.isEmpty()) {
            sb.append("\tNONE\n");
        } else {
            requiredTypeSet.forEach(rt -> sb.append("\t")
                    .append(ConversionUtils.enumToProperStr(rt)).append("\n"));
        }
        sb.append("2. Meal items that the user is allowed to order multiple of:\n");
        if (setOfItemsThatCanBeMultiple.isEmpty()) {
            sb.append("\tNONE\n");
        } else {
            setOfItemsThatCanBeMultiple.forEach(im -> sb.append("\t").append(im).append("\n"));
        }
        sb.append("3. Meal item types that the user is allowed to order multiple of:\n");
        if (setOfTypesThatCanBeMultiple.isEmpty()) {
            sb.append("\tNONE\n");
        } else {
            setOfTypesThatCanBeMultiple.forEach(tm -> sb.append("\t")
                    .append(ConversionUtils.enumToProperStr(tm)).append("\n"));
        }
        sb.append("4. Meal item defaults:");
        if (defaultMap.isEmpty()) {
            sb.append("\tNONE\n");
        } else {
            defaultMap.forEach((type, item) -> sb.append("\n\t")
                    .append(ConversionUtils.enumToProperStr(type))
                    .append(":\n\t\t").append(item));
        }
        sb.append("\n5. Items the user gets for free with the meal if absent from order:\n");
        if (addedIfAbsent.isEmpty()) {
            sb.append("\tNONE");
        } else {
            addedIfAbsent.forEach(item -> sb.append("\t").append(item).append("\n"));
        }
        return sb.toString();
    }

}
