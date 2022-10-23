package main;

import main.api.db.MockDatabaseImpl;
import main.api.db.tables.Admins;
import main.api.domain.Meal;
import main.api.domain.MealOrder;
import main.api.domain.MealType;
import main.api.exceptions.ErrorsException;
import main.api.exceptions.InvalidInputException;
import main.api.services.MealService;
import main.api.services.MealServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    public static void driver() {
        testConvertInputOrder1();
        testConvertInputOrder2();
        testConvertInputOrder3();
        testCreateMeal1();
        testCreateMeal2();
        testAdminsDB1();
        testAdminsDB2();
    }

    public static void testAdminsDB1() {
        Admins admins = new Admins();
        admins.add("john", "password");
        if (admins.authenticated("john", "password")) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }

    public static void testAdminsDB2() {
        Admins admins = new Admins();
        admins.add("john", "password");
        if (admins.authenticated("john", "hello")) {
            System.out.println("Failed");
        } else {
            System.out.println("Passed");
        }
    }

    public static void testCreateMeal1() {
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        try {
            List<String> expectedOutputs = new ArrayList<>();
            expectedOutputs.add("Eggs, Toast, Coffee");
            expectedOutputs.add("Eggs, Toast, Coffee");
            expectedOutputs.add("Eggs, Toast, Coffee(3)");
            expectedOutputs.add("Sandwich, Chips, Soda");
            expectedOutputs.add("Sandwich, Chips, Water");
            expectedOutputs.add("Sandwich, Chips(2), Water");
            expectedOutputs.add("Steak, Potatoes, Wine, Water, Cake");
            List<String> inputs = new ArrayList<>();
            inputs.add("Breakfast 1,2,3");
            inputs.add("Breakfast 2,3,1");
            inputs.add("Breakfast 1,2,3,3,3");
            inputs.add("Lunch 1,2,3");
            inputs.add("Lunch 1,2");
            inputs.add("Lunch 1,2,2");
            inputs.add("Dinner 1,2,3,4");
            List<Meal> meals = new ArrayList<>();
            inputs.forEach(input -> {
                MealOrder mealOrder = mealService.convertInputToOrder(input);
                Meal meal = mealService.orderMeal(mealOrder);
                meals.add(meal);
            });
            List<Integer> wrongIndexes = new ArrayList<>();
            for (int i = 0; i < expectedOutputs.size(); i++) {
                if (!expectedOutputs.get(i).equals(meals.get(i).toString())) {
                    wrongIndexes.add(i);
                }
            }
            if (wrongIndexes.isEmpty()) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                wrongIndexes.forEach(wrongIndex -> {
                    System.out.println("\tExpected: " + expectedOutputs.get(wrongIndex));
                    System.out.println("\t\tGot: " + meals.get(wrongIndex).toString());
                });
            }
        } catch (InvalidInputException e) {
            System.out.println("Failed with exception");
            e.getErrors().forEach(error -> System.out.println("\t" + error));
        }
    }

    public static void testCreateMeal2() {
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        List<String> expectedOutputs = new ArrayList<>();
        expectedOutputs.add("Unable to process: Side is missing");
        expectedOutputs.add("Unable to process: Main cannot be ordered more than once, " +
                "sandwich cannot be ordered more than once");
        expectedOutputs.add("Unable to process: Main is missing, side is missing");
        expectedOutputs.add("Unable to process: Dessert is missing");
        List<String> inputs = new ArrayList<>();
        inputs.add("Breakfast 1");
        inputs.add("Lunch 1,1,2,3");
        inputs.add("Lunch");
        inputs.add("Dinner 1,2,3");
        List<String> actualOutputs = new ArrayList<>();
        for (int i = 0; i < expectedOutputs.size(); i++) {
            try {
                MealOrder order = mealService.convertInputToOrder(inputs.get(i));
                mealService.orderMeal(order);
            } catch (InvalidInputException e) {
                actualOutputs.add(e.toString());
            }
        }
        if (expectedOutputs.equals(actualOutputs)) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
            for (int i = 0; i < expectedOutputs.size(); i++) {
                String expected = expectedOutputs.get(i);
                String actual = actualOutputs.get(i);
                if (!expected.equals(actual)) {
                    System.out.println("\tMISMATCH:");
                    System.out.println("\t\tExpected: " + expected);
                    System.out.println("\t\tActual: " + actual);
                }
            }
        }
    }

    public static void testConvertInputOrder1() {
        MealOrder controlMealOrder1 = new MealOrder(MealType.BREAKFAST, new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }});
        MealOrder controlMealOrder2 = new MealOrder(MealType.LUNCH, new ArrayList<Integer>() {{
            add(2);
            add(3);
        }});
        MealOrder controlMealOrder3 = new MealOrder(MealType.BREAKFAST, new ArrayList<Integer>() {{
            add(3);
            add(1);
            add(2);
        }});
        MealOrder controlMealOrder4 = new MealOrder(MealType.BREAKFAST, new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(3);
        }});
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        String input = "Breakfast 1,2,3";
        try {
            MealOrder testMealOrder = mealService.convertInputToOrder(input);
            if (controlMealOrder1.equals(testMealOrder) && !controlMealOrder2.equals(testMealOrder) &&
                    controlMealOrder3.equals(testMealOrder) && !controlMealOrder4.equals(testMealOrder)) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
            }
        } catch (ErrorsException e) {
            System.out.println("Failed with exception");
            e.getErrors().forEach(error -> System.out.println("\t" + error));
        }
    }

    public static void testConvertInputOrder2() {
        MealService mealService = new MealServiceImpl(new MockDatabaseImpl());
        List<String> inputs = new ArrayList<>();
        inputs.add("Breakfast 1,2,3");
        inputs.add("BrEaKfAsT 1,2,3");
        inputs.add("lUnCh    3,3,3");
        inputs.add("   DiNNeR    5,4,100000");
        List<Integer> errorIndexes = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            try {
                mealService.convertInputToOrder(inputs.get(i));
            } catch (Exception e) {
                errorIndexes.add(i);
            }
        }
        if (errorIndexes.isEmpty()) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed: " + errorIndexes);
        }
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
