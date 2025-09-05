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
    private ArrayList<Task> things = new ArrayList<>(100);

    public TaskManager() {
        things = new ArrayList<>();
    }

    public TaskManager(ArrayList<Task> savedTasks) {
        things = savedTasks;
    }

    /**
     * Adds a task to the list.
     * @param curTask the task to be added
     */
    public void addTask(Task curTask) {
        things.add(curTask);
        System.out.println("added: " + curTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * Prints all tasks in the list in numbered order.
     */
    public void listTasks() {
        for (int i = 1; i <= things.size(); i++) {
            Task curTask = things.get(i - 1);
            System.out.println(i + ". " + curTask);
        }
    }

    /**
     * Marks a task as done.
     * @param index the 1-based index of the task to mark as done
     */
    public void markDone(int index) {
        Task markedTask = things.get(index - 1);
        markedTask.markDone();
        System.out.println("Good job! I have marked this task as done:");
        System.out.println(markedTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * Marks a task as not done.
     * @param index the 1-based index of the task to mark as not done
     */
    public void markNotDone(int index) {
        Task unmarkTask = things.get(index - 1);
        unmarkTask.markNotDone();
        System.out.println("Okay, I have marked this task as not done:");
        System.out.println(unmarkTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * Removes a task from the list.
     * @param index the 1-based index of the task to remove
     */
    public void removeTask(int index) {
        Task removedTask = things.get(index - 1);
        things.remove(index - 1);
        System.out.println("Okay, I have deleted this task:");
        System.out.println(removedTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * Saves the current list of tasks to the specified file.
     * @param file the file to save tasks to
     */
    void saveTasksToFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (Task t : things) {
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
        return things;
    }

    /**
     * Finds and prints tasks whose descriptions contain the given keyword.
     * @param keyword the keyword to search for
     */
    public void findTasks(String keyword) {
        System.out.println("Here are the matching tasks in your list:");
        int count = 0;
        for (int i = 0; i < things.size(); i++) {
            Task t = things.get(i);
            if (t.task.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println((count + 1) + ". " + t);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No tasks match your search.");
        }
    }

    /**
     * ss
     * @return
     */
    public String listTasksAsString() {
        if (things.isEmpty()) {
            return "No tasks in your list.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < things.size(); i++) {
            sb.append(i + 1).append(". ").append(things.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * aa
     * @param task
     * @return
     */
    public String addTaskAsString(Task task) {
        things.add(task);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Added: " + task;
    }

    /**
     * aa
     * @param index
     * @return
     */
    public String markDoneAsString(int index) {
        Task t = things.get(index - 1);
        t.markDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as done:\n" + t;
    }

    /**
     * aa
     * @param index
     * @return
     */
    public String markNotDoneAsString(int index) {
        Task t = things.get(index - 1);
        t.markNotDone();
        saveTasksToFile(new File("./data/nila.txt"));
        return "Marked as not done:\n" + t;
    }

    /**
     * aa
     * @param index
     * @return
     */
    public String removeTaskAsString(int index) {
        Task removed = things.remove(index - 1);
        saveTasksToFile(new File("./data/nila.txt"));
        return "Deleted:\n" + removed;
    }

    /**
     * aa
     * @param keyword
     * @return
     */
    public String findTasksAsString(String keyword) {
        StringBuilder sb = new StringBuilder("Matching tasks:\n");
        int count = 0;
        for (Task t : things) {
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
