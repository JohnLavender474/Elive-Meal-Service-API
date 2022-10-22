package main.api.factories;

import main.api.domain.Meal;

import java.util.Collection;

/** Standard implementation for {@link MealFactory}. */
public class StandardMealFactoryImpl implements MealFactory {

    @Override
    public String getMenuSelection() {
        return null;
    }

    @Override
    public boolean validateInput(String input, Collection<String> errors) {
        return false;
    }

    @Override
    public Meal createMeal(String input) {
        return null;
    }

}
