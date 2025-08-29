package nila.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 */
public class Deadline extends Task {
    private LocalDateTime dateTime; // optional
    private LocalDate date;

    /**
     * Creates a {@code Deadline} task with a due date and time.
     * @param taskName description of the task
     * @param dt the deadline as a {@link LocalDateTime}
     */
    public Deadline(String taskName, LocalDateTime dt) {
        super(taskName);
        dateTime = dt;
    }

    /**
     * Creates a {@code Deadline} task with a due date.
     * @param taskName description of the task
     * @param d deadline as a {@link LocalDate}
     */
    public Deadline(String taskName, LocalDate d) {
        super(taskName);
        date = d;
    }

    @Override
    public String toString() {
        String taskType = "❗";
        String when = (dateTime != null)
                ? dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"))
                : date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return taskType + " " + super.toString() + " (by: " + when + ")";
    }

    /**
     * Converts the deadline task into a saveable string format
     * for storage in a file.
     * @return the string representation of the deadline task in save format
     */
    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        if (dateTime != null) {
            return "D|" + isDone + "|" + this.task + "|" + dateTime.toString();
        }
        return "D|" + isDone + "|" + this.task + "|" + date.toString();
    }
}

