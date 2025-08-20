public class Event extends Task{
    private String start;
    private String end;

    Event(String task, String from, String to) {
        super(task);
        start = from;
        end = to;
    }

    public String toString() {
        String taskType = "\uD83D\uDCC5";
        String time = " (from: " + start + " to: " + end + ")";
        return taskType + " " + super.toString() + time;
    }
}
