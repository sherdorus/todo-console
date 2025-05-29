package todo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Helper {
    private static List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final File file = new File("todo_file.dat");

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
                        showAllTasks();
                        saveFile();
                        break;
                    case 2:
                        filterByStatus();
                        saveFile();
                        break;
                    case 3:
                        addTask();
                        saveFile();
                        break;
                    case 4:
                        editTask();
                        saveFile();
                        break;
                    case 5:
                        changeTaskStatus();
                        saveFile();
                        break;
                    case 6:
                        searchTasksByKeyword();
                        break;
                    case 7:
                        deleteTask();
                        saveFile();
                        break;
                    case 8:
                        deleteAllTasks();
                        saveFile();
                        break;
                    case 9:
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
        System.out.println("1. Show all tasks");
        System.out.println("2. Filter tasks by status");
        System.out.println("3. Add task");
        System.out.println("4. Edit task");
        System.out.println("5. Change task status");
        System.out.println("6. Search tasks by keyword");
        System.out.println("7. Delete task");
        System.out.println("8. Delete all tasks");
        System.out.println("9. Exit");
        System.out.print("Choose option: ");
    }

    protected static void loadFile(File file) {
        if (!file.exists()) {
            System.out.println("No task file found. Starting with an empty task list.");
            return;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            tasks = (List<Task>) inputStream.readObject();
            System.out.println("Tasks loaded successfully from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Task file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading task file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Task class not found or incompatible: " + e.getMessage());
        }
    }

    private static void saveFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            outputStream.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
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
            tasks.add(new Task(taskInput, getCurrentDate()));
            System.out.println("Task '" + taskInput + "' added successfully!");
            while (loopStatus) {
                System.out.println("------------------------------------------");
                System.out.println("Do you want to add more tasks? Type Y or N");
                System.out.println("------------------------------------------");
                String moreTask = scanner.nextLine().toUpperCase();
                if (moreTask.equals("Y") || moreTask.equals("YES")) {
                    System.out.print("Enter task information: ");
                    taskInput = scanner.nextLine();
                    tasks.add(new Task(taskInput, getCurrentDate()));
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

    private static void editTask() {
        if (tasks.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.println("-----------------------");
            System.out.println("Choose a task for edit:");
            System.out.println("-----------------------");
            showAllTasks();
            try {
                int chooseTask = scanner.nextInt();
                scanner.nextLine();
                if (chooseTask > 0 && chooseTask <= tasks.size()) {
                    System.out.println("Enter new task information: ");
                    String newTask = scanner.nextLine();
                    tasks.get(chooseTask - 1).setTask(newTask);
                    System.out.println("Task edited successfully");
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
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
                TaskStatus status = getStatusFromInput(chooseStatus);
                scanner.nextLine();
                if (status != null) {
                    tasks.get(chooseTask - 1).setTaskStatus(status);
                    System.out.println("Task status updated successfully!");
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
            try {
                int chooseTask = scanner.nextInt();
                scanner.nextLine();
                if (chooseTask > 0 && chooseTask <= tasks.size()) {
                    System.out.println("Are you sure you want to delete this task? Y/N:");
                    String confirm = scanner.nextLine().toUpperCase();
                    if (confirm.equals("Y") || confirm.equals("YES")) {
                        tasks.remove(chooseTask - 1);
                        System.out.println("Task deleted successfully!");
                    }
                    showAllTasks();
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
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

    private static TaskStatus getStatusFromInput(int input) {
        if (input > 0 && input <= TaskStatus.values().length) {
            return TaskStatus.values()[input - 1];
        }
        System.out.println("Invalid status number!");
        return null;
    }

    private static void filterByStatus() {
        System.out.println("--------------------------------");
        System.out.println("Choose a status to filter tasks:");
        System.out.println("--------------------------------");
        showStatus();
        try {
            int input = scanner.nextInt();
            scanner.nextLine();
            TaskStatus status = getStatusFromInput(input);
            if (status == null) return;
            boolean found = false;
            for (Task task : tasks) {
                if (task.getTaskStatus() == status) {
                    System.out.println(task);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No tasks found with status: " + status);
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
    }

    private static void searchTasksByKeyword() {
        System.out.println("--------------------------------");
        System.out.println("Enter keyword to search:");
        System.out.println("--------------------------------");
        String keyword = scanner.next();
        boolean found = false;
        for (Task task : tasks) {
            if (task.getTask().toLowerCase().contains(keyword)) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with keyword: " + keyword);
        }
    }
}