package nila.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages a list of tasks, providing functionality to add, list, mark,
 * unmark and remove tasks.
 */
public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>(100);

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     * @param curTask the task to be added
     */
    public void addTask(Task curTask) {
        tasks.add(curTask);
    }

    /**
     * Saves the current list of tasks to the specified file.
     * @param file the file to save tasks to
     */
    void saveTasksToFile(File file) {
        assert file != null : "File cannot be null";
        try (FileWriter writer = new FileWriter(file)) {
            for (Task t : tasks) {
                writer.write(t.toSaveFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Returns the current list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a formatted string representation of all tasks in the list.
     * Each task is numbered sequentially. If the task list is empty,
     * returns a message indicating no tasks are present.
     * @return a formatted string listing all tasks, or a message indicating
     *     the task list is empty if no tasks exist.
     */
    public String listTasksAsString() {
        if (tasks.isEmpty()) {
            return "No tasks in your list.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Adds a task to the task list and saves the updated list to file.
     * @param task the task to be added
     * @return a confirmation message containing the added task
     */
    public String addTaskAsString(Task task) {
        tasks.add(task);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Added: " + task;
    }

    /**
     * Marks a task as done and saves the change.
     * @param index the 1-based index of the task to mark as done
     * @return a confirmation message containing the marked task
     */
    public String markDoneAsString(int index) {
        assert index <= things.size() : "Index cannot exceed task list size";
        Task t = tasks.get(index - 1);
        t.markDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as done:\n" + t;
    }

    /**
     * Marks a task as not done and saves the change.
     * @param index the 1-based index of the task to mark as not done
     * @return a confirmation message containing the unmarked task
     */
    public String markNotDoneAsString(int index) {
        assert index <= things.size() : "Index cannot exceed task list size";
        Task t = tasks.get(index - 1);
        t.markNotDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as not done:\n" + t;
    }

    /**
     * Removes a task from the list and saves the updated list.
     * @param index the 1-based index of the task to remove
     * @return a confirmation message containing the deleted task
     */
    public String removeTaskAsString(int index) {
        Task removed = tasks.remove(index - 1);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Deleted:\n" + removed;
    }

    /**
     * Searches for tasks whose descriptions contain the given keyword
     * (case-insensitive) and returns a formatted list of matching tasks.
     * @param keyword the search term to match against task descriptions
     * @return a formatted string containing matching tasks, or a message
     *         indicating no matches were found
     */
    public String findTasksAsString(String keyword) {
        StringBuilder sb = new StringBuilder("Matching tasks:\n");
        int count = 0;
        for (Task t : tasks) {
            if (t.task.toLowerCase().contains(keyword.toLowerCase())) {
                sb.append((count + 1)).append(". ").append(t).append("\n");
                count++;
            }
        }
        if (count == 0) {
            sb.append("No tasks match your search.");
        }
        return sb.toString();
    }

}
