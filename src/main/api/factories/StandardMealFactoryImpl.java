package main.api.factories;

import main.api.domain.*;
import main.api.exceptions.InvalidInputException;

import java.util.*;

/**
 * Standard implementation for {@link MealFactory}.
 */
public class StandardMealFactoryImpl implements MealFactory {

    private final Map<Integer, MealItem> idToItemMap = new HashMap<>();
    // Use tree map to preserve natural ordering of enum values
    private final Map<MealItemType, Set<MealItem>> typeToItemSetMap = new TreeMap<>();
    private final MealRules rules;

    /**
     * Constructor for the standard implementation of meal factory.
     *
     * @param items the meal items
     * @param rules the meal rules
     */
    public StandardMealFactoryImpl(Collection<MealItem> items, MealRules rules) {
        items.forEach(item -> {
            idToItemMap.put(item.getId(), item);
            // use tree set for keys to maintain sorting of items
            typeToItemSetMap.computeIfAbsent(item.getType(), k -> new TreeSet<>()).add(item);
        });
        this.rules = rules;
    }

    @Override
    public Map<MealItemType, Set<MealItem>> getMenuSelection() {
        return Collections.unmodifiableMap(typeToItemSetMap);
    }

    @Override
    public Meal createMeal(MealOrder order) throws InvalidInputException {
        // Use tree map to preserve ordering of enums in their declarations
        SortedMap<MealItem, Integer> meal = new TreeMap<>();
        Set<MealItemType> typesInRequestedMeal = new HashSet<>();
        List<String> errors = new ArrayList<>();
        // Iterate over each item id provided in the meal order, check if the item and item type are allowed
        // to be multiple if there are multiple, and add the item to the meal map if passes
        order.getItemIds().forEach(itemId -> {
            MealItem item = idToItemMap.get(itemId);
            // If item is null, then the id is invalid
            if (item == null) {
                errors.add("Unknown item id: " + itemId);
                return;
            }
            // Check if meal rules allows multiple of item
            if (typesInRequestedMeal.contains(item.getType()) && !rules.canBeMultiple(item.getType()) &&
                    !rules.canBeMultiple(item)) {
                errors.add("Menu rules states you can't have more than one of item type <" + item.getType() +
                        "> and you can't have more than one of <" +  item + ">");
            }
            typesInRequestedMeal.add(item.getType());
            // Check if meal rules allows for multiple of item
            int count = meal.getOrDefault(item, 0) + 1;
            if (count > 1 && !rules.canBeMultiple(item.getType()) && !rules.canBeMultiple(item)) {
                errors.add("Menu rules states you can't have more than one of item type <" + item.getType() +
                        "> and you can't have more than one of <" +  item + ">");
            }
            meal.put(item, count);
        });
        // Check for defaults and add if applicable
        rules.getItemTypesWithDefaultVals().forEach(itemType -> {
            if (!typesInRequestedMeal.contains(itemType)) {
                Optional<MealItem> optionalMealItem = rules.getDefault(itemType);
                if (optionalMealItem.isPresent()) {
                    MealItem defaultMealItem = optionalMealItem.get();
                    int count = meal.getOrDefault(defaultMealItem, 0);
                    meal.put(defaultMealItem, count + 1);
                    typesInRequestedMeal.add(itemType);
                }
            }
        });
        // Add extra items if applicable
        rules.getItemsToBeAddedIfAbsent().forEach(item -> {
            if (!meal.containsKey(item)) {
                meal.put(item, 1);
            }
        });
        // Check for the required types that are missing, add error is missing types set is not empty
        Set<MealItemType> missingTypes = new HashSet<>();
        if (!rules.satisfiesRequiredTypes(typesInRequestedMeal, missingTypes)) {
            missingTypes.forEach(missingType ->
                    errors.add("Meal item type " + missingType + " requires user selection!"));
        }
        // If there are any errors, then we are not allowed to instantiate a meal with the given meal order
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
        return new Meal(meal);
    }

}
