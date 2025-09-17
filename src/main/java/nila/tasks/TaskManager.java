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
     * Prints all tasks in the list in numbered order
     * @return a list of task as a String
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
     * Adds a task to the list.
     * @param task the task to be added
     * @return a String to inform user that the task was successfully added
     */
    public String addTaskAsString(Task task) {
        tasks.add(task);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Added: " + task;
    }

    /**
     * Marks a task as done.
     * @param index the 1-based index of the task to mark as done
     * @return a String to inform user that the task was successfully marked
     */
    public String markDoneAsString(int index) {
        Task t = tasks.get(index - 1);
        t.markDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as done:\n" + t;
    }

    /**
     * Marks a task as not done.
     * @param index the 1-based index of the task to mark as not done
     * @return a String to inform user that the task was successfully unmarked
     */
    public String markNotDoneAsString(int index) {
        Task t = tasks.get(index - 1);
        t.markNotDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as not done:\n" + t;
    }

    /**
     * Removes a task from the list.
     * @param index the 1-based index of the task to remove
     * @return a String to inform user that the task was deleted
     */
    public String removeTaskAsString(int index) {
        Task removed = tasks.remove(index - 1);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Deleted:\n" + removed;
    }

    /**
     * Finds and prints tasks whose descriptions contain the given keyword.
     * @param keyword the keyword to search for
     * @return tasks that contain the keyword in their description
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
