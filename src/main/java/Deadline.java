public class Deadline extends Task{
    private String deadline;

    Deadline(String taskName, String dueBy) {
        super(taskName);
        deadline = dueBy;
    }

    @Override
    public String toString() {
        String taskType = "❗";
        return taskType + " " + super.toString() + " (by: " + deadline + ")";
    }

    @Override
    String toSaveFormat() {
        int isDone = (this.isDone) ? 1 : 0;
        return "D|" + isDone + "|" + this.task + "|" + deadline;
    }
}

