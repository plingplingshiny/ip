import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class TaskManager {
    ArrayList<Task> things = new ArrayList<>(100);

    TaskManager() {

    }

    void addTask(Task curTask) {
        things.add(curTask);
        System.out.println("added: " + curTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    void listTasks() {
        for (int i = 1; i <= things.size(); i++) {
            Task curTask = things.get(i-1);
            System.out.println(i + ". " + curTask);
        }
    }

    void markDone(int index) {
        Task markedTask = things.get(index-1);
        markedTask.markDone();
        System.out.println("Good job! I have marked this task as done:");
        System.out.println(markedTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    void markNotDone(int index) {
        Task unmarkTask = things.get(index-1);
        unmarkTask.markNotDone();
        System.out.println("Okay, I have marked this task as not done:");
        System.out.println(unmarkTask);
        saveTasksToFile(new File("./data/nila.txt"));
    }

    void removeTask(int index) {
        Task removedTask = things.get(index-1);
        things.remove(index-1);
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

    void loadTasksFromFile(File file) {
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task t = Task.fromSaveFormat(line);
                if (t != null) {
                    things.add(t);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found, starting with empty task list.");
        }
    }

    public ArrayList<Task> getTasks() {
        return things;
    }
}
