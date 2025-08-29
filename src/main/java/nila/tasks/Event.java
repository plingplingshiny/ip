package nila.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates an {@code Event} task with start and end timings.
 */
public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates an {@code Event} task with a start and end time.
     * @param task description of event task
     * @param from start date and time of event
     * @param to end date and time of event
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

    /**
     * Converts the event task into a saveable string format.
     * for storage in a file.
     * @return the string representation of the event task in save format
     */
    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        return "E|" + isDone + "|" + this.task + "|" + start.toString() + "|" + end.toString();
    }
}
