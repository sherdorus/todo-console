package todo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Helper {
    private static List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final File file = new File("todo_file");
    public static File getFile() {
        return file;
    }

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
                        showAllTasks();
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
                        saveFile();
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

    protected static void loadFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            tasks = (List<Task>) inputStream.readObject();
        } catch (Exception e) {
            System.out.println("Cannot find the file: " + e.getMessage());

        }
    }

    private static void saveFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Helper.file)))) {
            outputStream.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Couldn't write the file out: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
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

    private static void showAllTasks() {
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
            showAllTasks();
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
                            showAllTasks();
                            break;
                        }
                        case 2: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.COMPLETED);
                            showAllTasks();
                            break;
                        }
                        case 3: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.PROGRESS);
                            showAllTasks();
                            break;
                        }
                        case 4: {
                            tasks.get(chooseTask - 1).setTaskStatus(TaskStatus.STARRED);
                            showAllTasks();
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
            showAllTasks();
            int chooseTask = scanner.nextInt();
            if (chooseTask > 0 && chooseTask <= tasks.size()) {
                tasks.remove(chooseTask - 1);
                System.out.println("Task deleted successfully!");
                showAllTasks();
            } else {
                System.out.println("Invalid task number!");
            }
        }
    }

    private static void deleteAllTasks() {
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            showAllTasks();
            System.out.println("Delete all tasks? Type Y or N:");
            String chooseTask = scanner.nextLine().toUpperCase();
            if (chooseTask.equals("Y") || chooseTask.equals("YES")) {
                tasks.clear();
                System.out.println("All tasks deleted!");
                showAllTasks();
            }
        }
    }
}
