public class Task {
    protected String task;
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

    String toSaveFormat() {
        return "";
    }

    static Task fromSaveFormat(String line) {
        String[] curLine = line.split("\\|");
        String command = curLine[0];
        boolean isDone = curLine[1].trim().equals("1");
        String description = curLine[2];
        Task savedTask;
        if (command.equals("T")) {
            savedTask = new Todo(description);
        } else if (command.equals("D")) {
            String dueBy = curLine[3];
            savedTask = new Deadline(description, dueBy);
        } else {
            String from = curLine[2];
            String to = curLine[3];
            savedTask = new Event(description, from, to);
        }
        if (isDone) {
            savedTask.markDone();
        }
        return savedTask;
    }
}
