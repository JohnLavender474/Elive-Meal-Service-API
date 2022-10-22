package main.api.services;

import main.api.domain.Meal;
import main.api.domain.MealType;

import java.util.Collection;

/** Implementation of interface {@link MealService}. */
public class MealServiceImpl implements MealService {

    @Override
    public boolean validateInput(String input, Collection<String> errors) {
        return false;
    }

    @Override
    public Meal orderMeal(String input) {
        return null;
    }

    @Override
    public void addMealOption(String input) {

    }

    @Override
    public void removeMealOption(MealType type, int id) {

    }

}
