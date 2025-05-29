# TodoConsole 📋

## Description

**TodoConsole** is a Java-based command-line application for managing tasks efficiently and intuitively.  
It allows users to create, update, delete, and search tasks with various statuses — all from a simple console menu.  
This project demonstrates key Java concepts such as object-oriented design, file I/O, exception handling, and interactive user input.

## Features ✨

- **Task Management**: Create, edit, delete individual tasks, or clear all tasks.
- **Status Tracking**: Assign and update task statuses (`Queue`, `Completed`, `In Progress`, `Starred`).
- **Filtering and Search**: Filter tasks by status or search task descriptions by keyword (case-insensitive).
- **Persistent Storage**: Save tasks automatically to `todo_file.dat` after each action, with robust loading at startup.
- **User-Friendly Interface**: Interactive console menu with input validation and confirmation prompts for critical actions (e.g., deletion).
- **Error Handling**: Graceful handling of invalid inputs and file operations.

## Requirements

- Java 8 or higher
- IntelliJ IDEA (recommended) or any Java-compatible IDE
- Git (for cloning the repository)

## Setup 🛠️

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/yourusername/todo-console.git
   ```
2. **Open in IntelliJ IDEA**:
   - Open IntelliJ IDEA.
   - Select `File` → `Open` and navigate to the cloned `todo-console` directory.
3. **Run the Application**:
   - Locate `Main.java` in the `src/todo` directory.
   - Right-click `Main.java` and select `Run 'Main.main()'`.
4. **Interact with the App**:
   - Follow the console menu to manage tasks.

## Usage 💻

After running `Main.java`, the app loads existing tasks from `todo_file.dat` (if available) and displays a menu with the following options:

1. Show all tasks
2. Filter tasks by status
3. Add task
4. Edit task
5. Change task status
6. Delete task
7. Delete all tasks
8. Exit
9. Search tasks by keyword

**Example**:

```plaintext
=== ToDo List ===
1. Show all tasks
2. Filter tasks by status
3. Add task
4. Edit task
5. Change task status
6. Delete task
7. Delete all tasks
8. Exit
9. Search tasks by keyword
Choose option: 3
-----------------------
Enter task information:
-----------------------
Buy groceries
Task 'Buy groceries' added successfully!
```

## Project Structure 📂

- `src/todo/Main.java`: Entry point, initializes task loading and starts the menu.
- `src/todo/Task.java`: Represents a task with description, creation date, and status.
- `TaskStatus`: Enum defining task statuses (`IN_QUEUE`, `COMPLETED`, `PROGRESS`, `STARRED`).
- `src/todo/Helper.java`: Core logic for task management, file I/O, and user interaction.
- `todo_file.dat`: Stores serialized tasks (created automatically).

## Contributing 🤝

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

Please ensure code follows Java conventions and includes clear commit messages.

## License 📜

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments 🙏

- Built as a learning project to practice Java, Git, and console-based app development.
- Feedback and suggestions are appreciated!