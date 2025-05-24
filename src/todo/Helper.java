package todo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Helper {
    private static List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void mainStarter() {
        while (true) {
            try {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        showTask();
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter numbers only, try again.");
                scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== ToDo List ===");
        System.out.println("1. Add task");
        System.out.println("2. Show all tasks");
        System.out.println("3. Change task status");
        System.out.println("4. Delete task");
        System.out.println("5. Delete all tasks");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");
    }

    private static void addTask() {}

    private static void showTask() {}
}
