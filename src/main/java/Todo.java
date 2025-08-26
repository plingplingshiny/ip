public class Todo extends Task{

    Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        String taskType = "\uD83D\uDCCB";
        return taskType + " " + super.toString();
    }

    @Override
    String toSaveFormat() {
        int isDone = (this.isDone) ? 1 : 0;
        return "T|" + isDone + "|" + this.task;
    }
}

