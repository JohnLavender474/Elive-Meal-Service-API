package main;

import main.api.db.MockDatabaseImpl;
import main.api.domain.MealOrder;
import main.api.domain.MealType;
import main.api.exceptions.ErrorsException;
import main.api.services.MealService;
import main.api.services.MealServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    public static void main(String[] args) {
        testConvertInputOrder1();
    }

    public static void testConvertInputOrder1() {
        MealOrder controlMealOrder = new MealOrder(MealType.BREAKFAST, new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }});
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        String input = "Breakfast 1,2,3";
        try {
            MealOrder testMealOrder = mealService.convertInputToOrder(input);
            if (controlMealOrder.equals(testMealOrder)) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
            }
            System.out.println("\tControl: " + controlMealOrder);
            System.out.println("\tTest: " + testMealOrder);
        } catch (ErrorsException e) {
            System.out.println("Failed with exception");
            e.getErrors().forEach(error -> System.out.println("\t" + error));
        }
    }

    public static void testConvertInputOrder2() {
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        List<String> inputs = new ArrayList<>();
    }

    public static void testConvertInputOrder3() {
        int exceptionCount = 0;
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        List<String> inputs = new ArrayList<>();
        inputs.add("Breakfast 1,2, 3");
        inputs.add("Lumch 1,2,3");
        inputs.add("dinner 1 2,3,4");
        for (String input : inputs) {
            try {
                mealService.convertInputToOrder(input);
            } catch (Exception e) {
                exceptionCount += 1;
            }
        }
        if (exceptionCount == 3) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }

}
