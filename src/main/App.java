package main;

import main.api.db.MockDatabaseImpl;
import main.api.domain.Meal;
import main.api.domain.MealOrder;
import main.api.exceptions.ErrorsException;
import main.api.services.MealService;
import main.api.services.MealServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point into the Fast Meal Service API app.
 */
public class App {

    private static final String LONG_DASHED_DELIM = "---------------------------------------------------------------";
    private static final String SHORT_DASHED_DELIM = "--------------------------";

    private static final class User {

        private final List<Meal> orders = new ArrayList<>();

    }

    public static void main(String[] args) {
        App app = new App(new User());
        app.printWelcome();
        app.entryPoint();
    }

    private final User user;
    private final Scanner scanner = new Scanner(System.in);
    private final MealService mealService = new MealServiceImpl(new MockDatabaseImpl());

    private App(User user) {
        this.user = user;
    }

    private void entryPoint() {
        while (true) {
            System.out.println("\n\n" + LONG_DASHED_DELIM);
            System.out.println("Options:");
            System.out.println("\t1. Order meals");
            System.out.println("\t2. Checkout");
            System.out.println("\t3. Login as admin");
            int option = 0;
            do {
                System.out.print("\nPlease enter [1,2,3]: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input!");
                    scanner.next();
                } else {
                    option = scanner.nextInt();
                    scanner.nextLine();
                }
            } while (!equalsAny(option, 1, 2, 3));
            switch (option) {
                case 1:
                    orderMealsEntryPoint();
                    break;
                case 2:
                    checkout();
                    break;
                case 3:
                    loginAsAdminEntryPoint();
                    break;
            }
        }
    }

    private void orderMealsEntryPoint() {
        while (true) {
            System.out.println("\n" + SHORT_DASHED_DELIM);
            System.out.println("Options:");
            System.out.println("\t1. Add new order");
            System.out.println("\t2. View current orders");
            System.out.println("\t3. Delete order");
            System.out.println("\t4. Return");
            int option = 0;
            do {
                System.out.print("\nPlease enter [1,2,3,4]: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input!");
                    scanner.next();
                } else {
                    option = scanner.nextInt();
                    scanner.nextLine();
                }
            } while (!equalsAny(option, 1, 2, 3, 4));
            switch (option) {
                case 1:
                    addNewOrder();
                    break;
                case 2:
                    viewCurrentOrders();
                    break;
                case 3:
                    deleteOrder();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void addNewOrder() {
        System.out.println("\n" + SHORT_DASHED_DELIM);
        System.out.print("Enter your order: ");
        String input = scanner.nextLine();
        try {
            MealOrder mealOrder = mealService.convertInputToOrder(input);
            Meal meal = mealService.orderMeal(mealOrder);
            user.orders.add(meal);
            System.out.println("\nSuccessfully added to your orders: " + meal);
        } catch (ErrorsException e) {
            System.err.println(e);
        }
    }

    private void viewCurrentOrders() {
        System.out.println("\n" + SHORT_DASHED_DELIM);
        System.out.println("Current orders:");
        for (int i = 1; i <= user.orders.size(); i++) {
            System.out.println("\t" + i + ". " + user.orders.get(i - 1));
        }
    }

    private void deleteOrder() {
        viewCurrentOrders();
        System.out.print("\nEnter order number to delete: ");
        while (!scanner.hasNextInt() || scanner.nextInt() < 1 || scanner.nextInt() > user.orders.size()) {
            scanner.next();
            System.out.print("\nInvalid input! Please enter a number between 1 and " + user.orders.size() + ": ");
        }
        int index = scanner.nextInt() - 1;
        Meal meal = user.orders.remove(index);
        System.out.println("Removed order: " + meal);
    }

    private void checkout() {

    }

    private void loginAsAdminEntryPoint() {

    }

    private void printWelcome() {
        System.out.println(LONG_DASHED_DELIM);
        System.out.println("FAST MEAL SERVICE API\n");
        System.out.println("Welcome to the \"Fast Meal Service Api\"!");
        System.out.println("\nThis project is written by John Lavender over the weekend of 10/22/2022 - 10/23/2022");
        System.out.println("as part of an internship application. Feel free to run this app as much as you like,");
        System.out.println("look for any ways you can break it, and have fun! :)");
        System.out.print("\n\nPress \"ENTER\" to continue: ");
        scanner.nextLine();
    }

    private static boolean equalsAny(int x, int... any) {
        for (int i : any) {
            if (x == i) {
                return true;
            }
        }
        return false;
    }

}
