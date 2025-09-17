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
        String[] curLine = line.split("\\|");
        String command = curLine[0];
        boolean isDone = curLine[1].trim().equals("1");
        String description = curLine[2];
        Task savedTask;
        if (command.equals("T")) {
            savedTask = new Todo(description);
        } else if (command.equals("D")) {
            String savedDate = curLine[3];
            try {
                LocalDateTime dt = LocalDateTime.parse(savedDate);
                savedTask = new Deadline(description, dt);
            } catch (DateTimeParseException e1) {
                LocalDate d = LocalDate.parse(savedDate);
                savedTask = new Deadline(description, d);
            }
        } else {
            String from = curLine[3];
            String to = curLine[4];
            LocalDateTime start = LocalDateTime.parse(from);
            LocalDateTime end = LocalDateTime.parse(to);
            savedTask = new Event(description, start, end);
        }
        if (isDone) {
            savedTask.markDone();
        }
        return savedTask;
    }
}
