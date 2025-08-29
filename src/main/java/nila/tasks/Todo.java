package nila.tasks;

/**
 * Represents a todo task
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

    /**
     * Converts the todo task into a saveable string format
     * for storage in a file.
     * @return the string representation of the todo task in save format
     */
    @Override
    public String toSaveFormat() {
        int isDone = (this.getIsDone()) ? 1 : 0;
        return "T|" + isDone + "|" + this.task;
    }
}

