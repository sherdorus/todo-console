package todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    case 3:
                        changeTaskStatus();
                        break;
                    case 4:
                        deleteTask();
                        break;
                    case 5:
                        deleteAllTasks();
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
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

    private static void emptyListMessage() {
        System.out.println("There are no tasks in the list. Add a new task!");
    }

    private static void addTask() {
        boolean loopStatus = true;
        System.out.println("-----------------------");
        System.out.println("Enter task information:");
        System.out.println("-----------------------");
        String taskInput = scanner.nextLine();
        if (taskInput.isEmpty()) {
            emptyListMessage();
        } else {
            tasks.add(new Task(taskInput, LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
            System.out.println("Task '" + taskInput + "' added successfully!");
            while (loopStatus) {
                System.out.println("------------------------------------------");
                System.out.println("Do you want to add more tasks? Type Y or N");
                System.out.println("------------------------------------------");
                String moreTask = scanner.nextLine().toUpperCase();
                if (moreTask.equals("Y") || moreTask.equals("YES")) {
                    System.out.print("Enter task information: ");
                    taskInput = scanner.nextLine();
                    tasks.add(new Task(taskInput, LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
                    System.out.println("Task '" + taskInput + "' added successfully!");
                } else {
                    System.out.println("Returning to main menu...");
                    loopStatus = false;
                }
            }
        }
    }

    private static void showTask() {
        int count = 1;
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            for (Task task : tasks) {
                System.out.println(count + ": " + task.toString());
                count++;
            }
        }
    }

    private static void showStatus() {
        int count = 1;
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            for (TaskStatus taskStatus : TaskStatus.values()) {
                System.out.println(count + ": " + taskStatus.toString());
                count++;
            }
        }
    }

    private static void changeTaskStatus() {
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.println("------------------------------------");
            System.out.println("Choose a task to change it's status:");
            System.out.println("------------------------------------");
            showTask();
            int chooseTask = scanner.nextInt();
            if (chooseTask > 0 && chooseTask <= tasks.size()) {
                System.out.println("--------------------------");
                System.out.println("Choose a status to assign:");
                System.out.println("--------------------------");
                showStatus();
                int chooseStatus = scanner.nextInt();
                scanner.nextLine();
                if (chooseStatus <= TaskStatus.values().length && chooseStatus > 0) {
                    switch (chooseStatus) {
                        case 1: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.IN_QUEUE);
                            showTask();
                            break;
                        }
                        case 2: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.COMPLETED);
                            showTask();
                            break;
                        }
                        case 3: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.PROGRESS);
                            showTask();
                            break;
                        }
                        case 4: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.STARRED);
                            showTask();
                            break;
                        }
                        default:
                            System.out.println("Invalid choice, please try again.");
                            break;
                    }
                } else {
                    System.out.println("Invalid status number!");
                }
            } else {
                System.out.println("Invalid task number!");
            }
        }
    }

    private static void deleteTask() {
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.println("------------------------");
            System.out.println("Choose a task to delete:");
            System.out.println("------------------------");
            showTask();
            int chooseTask = scanner.nextInt();
            if (chooseTask > 0 && chooseTask <= tasks.size()) {
                tasks.remove(chooseTask - 1);
                System.out.println("Task deleted successfully!");
                showTask();
            } else {
                System.out.println("Invalid task number!");
            }
        }
    }

    private static void deleteAllTasks() {
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            showTask();
            System.out.println("Delete all tasks? Type Y or N:");
            String chooseTask = scanner.nextLine().toUpperCase();
            if (chooseTask.equals("Y") || chooseTask.equals("YES")) {
                tasks.clear();
                System.out.println("All tasks deleted!");
                showTask();
            }
        }
    }
}
