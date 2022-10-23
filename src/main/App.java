package main;

import main.api.db.MockDatabaseImpl;
import main.api.domain.*;
import main.api.exceptions.ErrorsException;
import main.api.services.MealService;
import main.api.services.MealServiceImpl;
import main.api.utils.ConversionUtils;
import main.tests.Tests;
import main.utils.Globals;

import java.util.*;

/**
 * Entry point for the app.
 */
public class App {

    private static final class User {

        private final List<Meal> orders = new ArrayList<>();

    }

    /**
     * Main function. If "test" arg is provided, then run tests. Otherwise, run app.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("test")) {
            Tests.driver();
        } else {
            App app = new App(new User());
            app.printWelcome();
            app.entryPoint();
        }
    }

    private final User user;
    private final Scanner scanner = new Scanner(System.in);
    private final MealService mealService = new MealServiceImpl(new MockDatabaseImpl());

    private App(User user) {
        this.user = user;
    }

    private void entryPoint() {
        while (true) {
            System.out.println("\n\n" + Globals.DASHED_DELIM);
            System.out.println("Options:");
            System.out.println("\t1. Order meals");
            System.out.println("\t2. Checkout");
            int option;
            while (true) {
                System.out.print("\nPlease enter [1,2]: ");
                String input = scanner.nextLine();
                if (Globals.isNumericInput(input) && Globals.equalsAny((option = 
                        Globals.parseIntOrDefault(input, -1)), 1, 2, 3)) {
                    break;
                } else {
                    System.err.println("\nERROR: Invalid input! Please try again.");
                }
            }
            switch (option) {
                case 1:
                    orderMealsEntryPoint();
                    break;
                case 2:
                    checkout();
                    break;
            }
        }
    }

    private void orderMealsEntryPoint() {
        while (true) {
            System.out.println("\n" + Globals.DASHED_DELIM);
            System.out.println("Options:");
            System.out.println("\t1. View menu");
            System.out.println("\t2. Add new order");
            System.out.println("\t3. View current orders");
            System.out.println("\t4. Delete order");
            System.out.println("\t5. Return");
            int option;
            while (true) {
                System.out.print("\nPlease enter [1,2,3,4,5]: ");
                String input = scanner.nextLine();
                if (Globals.isNumericInput(input) && Globals.isInRange((option =
                        Globals.parseIntOrDefault(input, -1)), 1, 5)) {
                    break;
                } else {
                    System.err.println("\nERROR: Invalid input! Please try again.");
                }
            }
            switch (option) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    addNewOrder();
                    break;
                case 3:
                    viewCurrentOrders();
                    makeUserPressEnter();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void viewMenu() {
        System.out.println("\nPlease select which meal type you want to see the menu for.");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        int option;
        while (true) {
            System.out.print("\nSelect [1,2,3]: ");
            String input = scanner.nextLine();
            if (Globals.isNumericInput(input) && Globals.equalsAny((option =
                    Globals.parseIntOrDefault(input, -1)), 1, 2, 3)) {
                break;
            } else {
                System.err.println("\nERROR: Invalid input! Please try again");
            }
        }
        switch (option) {
            case 1:
                printMenu(MealType.BREAKFAST);
                break;
            case 2:
                printMenu(MealType.LUNCH);
                break;
            case 3:
                printMenu(MealType.DINNER);
                break;
        }
    }

    private void printMenu(MealType mealType) {
        System.out.println("\n" + Globals.DASHED_DELIM);
        Map<MealItemType, Set<MealItem>> menu = mealService.viewMenuFor(mealType);
        System.out.println(mealType + " MENU:");
        menu.forEach((itemType, items) -> {
            System.out.println("\n\t" + ConversionUtils.enumToProperStr(itemType) + ":");
            items.forEach(item -> System.out.println("\t\t" + item.getId() + ". " + item.getName()));
        });
        makeUserPressEnter();
    }

    private void addNewOrder() {
        System.out.println("\n" + Globals.DASHED_DELIM);
        printNewOrderHelp();
        while (true) {
            System.out.print("\nEnter your order (or enter \"x\" to cancel): ");
            String input = scanner.nextLine();
            if (input.replaceAll("\\s+", "").equalsIgnoreCase("x")) {
                return;
            }
            try {
                MealOrder mealOrder = mealService.convertInputToOrder(input);
                Meal meal = mealService.orderMeal(mealOrder);
                user.orders.add(meal);
                System.out.println("\nSuccessfully added to your orders: " + meal);
                makeUserPressEnter();
                return;
            } catch (ErrorsException e) {
                System.err.println(e);
            }
        }
    }

    private void printNewOrderHelp() {
        System.out.println("Enter your order in the following format:");
        System.out.println("<meal type> <item1>,<item2>,<item3>,...");
        System.out.println("\nThe first part of the input must be \"Breakfast\", \"Lunch\", or \"Dinner\",");
        System.out.println("and the second part must be a list of id values separated by commas with no spaces.");
        System.out.println("\nFor example, if you want to order a breakfast with one item 1, one item 2, and one " +
                "item 3, then enter:");
        System.out.println("\n\tBreakfast 1,2,3");
        System.out.println("\nOr if you want to order lunch with one item 1 and two of item 2, then enter:");
        System.out.println("\n\tLunch 1,2,2");
        System.out.println("\nIMPORTANT! This input is case sensitive and whitespace sensitive.");
        System.out.println("Enter your input in the EXACT format described!");
    }

    private void viewCurrentOrders() {
        System.out.println("\n" + Globals.DASHED_DELIM);
        System.out.println("Current orders:");
        for (int i = 1; i <= user.orders.size(); i++) {
            System.out.println("\t" + i + ". " + user.orders.get(i - 1));
        }
    }

    private void deleteOrder() {
        if (user.orders.isEmpty()) {
            System.err.println("\nUser orders is empty, nothing to delete!");
            makeUserPressEnter();
            return;
        }
        viewCurrentOrders();
        System.out.print("\nEnter order number to delete (or enter \"x\" to cancel): ");
        int option;
        while (true) {
            System.out.print("\nPlease enter order number: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("x")) {
                return;
            }
            if (Globals.isNumericInput(input) && Globals.isInRange((option =
                    Globals.parseIntOrDefault(input, -1)), 1, user.orders.size())) {
                break;
            } else {
                System.err.println("\nERROR: Invalid input! Please try again.");
            }
        }
        Meal meal = user.orders.remove(option - 1);
        System.out.println("\nRemoved order: " + meal);
        makeUserPressEnter();
    }

    private void checkout() {
        System.out.println("\n" + Globals.DASHED_DELIM);
        System.out.println("Here are your current orders:\n");
        for (int i = 1; i <= user.orders.size(); i++) {
            System.out.println("\t" + i + ". " + user.orders.get(i - 1));
        }
        System.out.println("\nAre you sure you want to check out now?");
        System.out.println("(All progress will be lost!)");
        String input;
        do {
            System.out.print("\n[y/n]: ");
            input = scanner.nextLine().toLowerCase();
            if (!input.equals("y") && !input.equals("n")) {
                System.err.println("\nInvalid input!");
            }
        } while (!input.equals("y") && !input.equals("n"));
        if (input.equals("y")) {
            System.exit(0);
        }
    }

    private void printWelcome() {
        System.out.println(Globals.DASHED_DELIM);
        System.out.println("FAST MEAL SERVICE API\n");
        System.out.println("Welcome to the \"Fast Meal Service Api\"!");
        System.out.println("\nThis project is written by John Lavender over the weekend of 10/22/2022 - 10/23/2022");
        System.out.println("as part of an internship application. Feel free to run this app as much as you like,");
        System.out.println("look for any ways you can break it, and have fun! :)");
        makeUserPressEnter();
    }

    private void makeUserPressEnter() {
        System.out.print("\n\nPress \"ENTER\" to continue: ");
        scanner.nextLine();
    }

}
