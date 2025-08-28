package nila.tasks;

/**
 * aa
 */
public class Todo extends Task {

    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        String taskType = "\uD83D\uDCCB";
        return taskType + " " + super.toString();
    }

    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        return "T|" + isDone + "|" + this.task;
    }
}

