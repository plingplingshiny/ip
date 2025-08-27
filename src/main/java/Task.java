import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Task {
    protected String task;
    public boolean isDone;

    Task(String taskName) {
        task = taskName;
        isDone = false;
    }

    public String toString() {
        String status = (isDone) ? "\uD83D\uDDF9 " : "☐ ";
        return status + task;
    }

    void markDone() {
        isDone = true;
    }

    void markNotDone() {
        isDone = false;
    }

    String toSaveFormat() {
        return "";
    }

    static Task fromSaveFormat(String line) {
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
            String from = curLine[2];
            String to = curLine[3];
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
