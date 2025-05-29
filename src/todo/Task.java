package todo;

import java.io.Serial;
import java.io.Serializable;

enum TaskStatus {
    IN_QUEUE, COMPLETED, PROGRESS, STARRED;

    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Queue";
            case 1 -> "Completed";
            case 2 -> "In Progress";
            case 3 -> "Starred";
            default -> null;
        };
    }
}

public class Task implements Serializable {
    @Serial
    private final static long serialVersionUID = 8827817261278643244L;
    private String task;
    private String date;
    private TaskStatus taskStatus;

    public Task(String task, String date) {
        this.date = date;
        this.task = task;
        this.taskStatus = TaskStatus.IN_QUEUE;
    }

    public void setTask(String task) {
        this.task = task;
    }

    protected void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Created: %s)", taskStatus, task, date != null ? date : "No date");
    }

    public String getTask() {
        return task;
    }
}