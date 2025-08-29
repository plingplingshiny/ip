package nila.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * aa
 */
public class TaskManager {
    private ArrayList<Task> things = new ArrayList<>(100);

    public TaskManager() {

    }

    /**
     * aa
     * @param curTask aa
     */
    public void addTask(Task curTask) {
        things.add(curTask);
        System.out.println("added: " + curTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * aa
     */
    public void listTasks() {
        for (int i = 1; i <= things.size(); i++) {
            Task curTask = things.get(i - 1);
            System.out.println(i + ". " + curTask);
        }
    }

    /**
     * aa
     * @param index aa
     */
    public void markDone(int index) {
        Task markedTask = things.get(index - 1);
        markedTask.markDone();
        System.out.println("Good job! I have marked this task as done:");
        System.out.println(markedTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * aa
     * @param index aa
     */
    public void markNotDone(int index) {
        Task unmarkTask = things.get(index - 1);
        unmarkTask.markNotDone();
        System.out.println("Okay, I have marked this task as not done:");
        System.out.println(unmarkTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    /**
     * aa
     * @param index aa
     */
    public void removeTask(int index) {
        Task removedTask = things.get(index - 1);
        things.remove(index - 1);
        System.out.println("Okay, I have deleted this task:");
        System.out.println(removedTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    void saveTasksToFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (Task t : things) {
                writer.write(t.toSaveFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

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
}
