package nila.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a general task with a description and completion status.
 */
public class Task {
    protected String task;
    private boolean isDone;

    Task(String taskName) {
        task = taskName;
        isDone = false;
    }

    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task.
     * @return A formatted string representing the task
     */
    public String toString() {
        String status = (isDone) ? "\uD83D\uDDF9 " : "☐ ";
        return status + task;
    }

    /**
     * Marks this task as completed.
     */
    void markDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    void markNotDone() {
        isDone = false;
    }

    /**
     * Converts the task into a string format for saving to a file.
     * @return The string representation of the task in save format
     */
    public String toSaveFormat() {
        return "";
    }

    /**
     * Reconstructs a {@code Task} object from its saved string format.
     * @param line A line from the saved file representing a task
     * @return the reconstructed {@code Task} object
     */
    public static Task fromSaveFormat(String line) {
        String[] components = line.split("\\|");
        String taskType = components[0];
        boolean isDone = components[1].trim().equals("1");
        String description = components[2];

        Task task = createTaskFromType(taskType, components, description);

        if (isDone) {
            task.markDone();
        }
        return task;
    }

    private static Task createTaskFromType(String taskType, String[] components, String description) {
        switch (taskType) {
        case "T":
            return new Todo(description);
        case "D":
            return createDeadlineTask(components, description);
        case "E":
            return createEventTask(components, description);
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    private static Task createDeadlineTask(String[] components, String description) {
        String savedDate = components[3];
        try {
            LocalDateTime dt = LocalDateTime.parse(savedDate);
            return new Deadline(description, dt);
        } catch (DateTimeParseException e) {
            LocalDate d = LocalDate.parse(savedDate);
            return new Deadline(description, d);
        }
    }

    private static Task createEventTask(String[] components, String description) {
        String from = components[3];
        String to = components[4];
        LocalDateTime start = LocalDateTime.parse(from);
        LocalDateTime end = LocalDateTime.parse(to);
        return new Event(description, start, end);
    }
}
