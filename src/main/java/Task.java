public class Task {
    private String task;
    public boolean isDone;

    Task(String taskName) {
        task = taskName;
        isDone = false;
    }

    public String toString() {
        String status = (isDone) ? "\uD83D\uDDF9 " : "☐ ";
        return status + task;
    }

    void markDone() {
        isDone = true;
    }

    void markNotDone() {
        isDone = false;
    }
}
