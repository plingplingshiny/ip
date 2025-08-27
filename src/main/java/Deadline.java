import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDateTime dateTime; // optional
    private LocalDate date;

    Deadline(String taskName, LocalDateTime dt) {
        super(taskName);
        dateTime = dt;
    }

    Deadline(String taskName, LocalDate d) {
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
    String toSaveFormat() {
        int isDone = (this.isDone) ? 1 : 0;
        if (dateTime != null) {
            return "D|" + isDone + "|" + this.task + "|" + dateTime.toString();
        }
        return "D|" + isDone + "|" + this.task + "|" + date.toString();
    }
}

