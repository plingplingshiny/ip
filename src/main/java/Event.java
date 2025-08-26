public class Event extends Task{
    private String start;
    private String end;

    Event(String task, String from, String to) {
        super(task);
        start = from;
        end = to;
    }

    @Override
    public String toString() {
        String taskType = "\uD83D\uDCC5";
        String time = " (from: " + start + " to: " + end + ")";
        return taskType + " " + super.toString() + time;
    }

    @Override
    String toSaveFormat() {
        int isDone = (this.isDone) ? 1 : 0;
        return "E|" + isDone + "|" + this.task + "|" + start + "|" + end;
    }
}
