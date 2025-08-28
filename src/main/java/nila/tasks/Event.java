package nila.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * aa
 */
public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * aa
     * @param task aa
     * @param from aa
     * @param to aa
     */
    public Event(String task, LocalDateTime from, LocalDateTime to) {
        super(task);
        start = from;
        end = to;
    }

    @Override
    public String toString() {
        String taskType = "\uD83D\uDCC5";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return taskType + " " + super.toString() + " (from: " + start.format(fmt)
                + " to: " + end.format(fmt) + ")";
    }

    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        return "E|" + isDone + "|" + this.task + "|" + start.toString() + "|" + end.toString();
    }
}
