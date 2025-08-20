public class Todo extends Task{

    Todo(String taskName) {
        super(taskName);
    }

    public String toString() {
        String taskType = "\uD83D\uDCCB";
        return taskType + " " + super.toString();
    }
}

