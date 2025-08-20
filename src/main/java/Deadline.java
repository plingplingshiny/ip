public class Deadline extends Task{
    private String deadline;

    Deadline(String taskName, String dueBy) {
        super(taskName);
        deadline = dueBy;
    }

    public String toString() {
        String taskType = "❗";
        return taskType + " " + super.toString() + " (by: " + deadline + ")";
    }
}

