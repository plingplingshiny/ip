import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private LocalDateTime start;
    private LocalDateTime end;

    Event(String task, LocalDateTime from, LocalDateTime to) {
        super(task);
        start = from;
        end = to;
    }

    @Override
    public String toString() {
        String taskType = "\uD83D\uDCC5";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return taskType + " " + super.toString() + " (from: " + start.format(fmt) +
                " to: " + end.format(fmt) + ")";
    }

    @Override
    String toSaveFormat() {
        int isDone = (this.isDone) ? 1 : 0;
        return "E|" + isDone + "|" + this.task + "|" + start.toString() + "|" + end.toString();
    }
}
