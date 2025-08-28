package nila.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * aa
 */
public class Deadline extends Task {
    private LocalDateTime dateTime; // optional
    private LocalDate date;

    /**
     * aa
     * @param taskName aa
     * @param dt aa
     */
    public Deadline(String taskName, LocalDateTime dt) {
        super(taskName);
        dateTime = dt;
    }

    /**
     * aa
     * @param taskName aa
     * @param d aa
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

    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        if (dateTime != null) {
            return "D|" + isDone + "|" + this.task + "|" + dateTime.toString();
        }
        return "D|" + isDone + "|" + this.task + "|" + date.toString();
    }
}

