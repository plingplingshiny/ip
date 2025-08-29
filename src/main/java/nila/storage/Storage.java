package nila.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import nila.tasks.Task;
import nila.tasks.TaskManager;

/**
 * Handles the saving and loading of tasks to and from a local file.
 */
public class Storage {
    private final File file;

    /**
     * aa
     * @param filePath aa
     */
    public Storage(String filePath) {
        file = new File(filePath);
        createIfNotExists();
    }

    private void createIfNotExists() {
        try {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating data file" + e.getMessage());
        }
    }

    /**
     * Saves all tasks from the {@link TaskManager} into the storage file.
     * @param taskList The {@code TaskManager} containing tasks to be saved
     */
    public void saveTasks(TaskManager taskList) {
        try (FileWriter fw = new FileWriter(file)) {
            for (Task task : taskList.getTasks()) {
                fw.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Load tasks from the storage file into a new {@link TaskManager}.
     * Each link in the file is converted back into a {@link Task}.
     * @return A {@code TaskManager} containing all loaded tasks.
     */
    public TaskManager loadTasks() {
        TaskManager taskList = new TaskManager();
        try (Scanner sc = new Scanner(file)) {
            System.out.println("Existing Tasks:");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = Task.fromSaveFormat(line);
                taskList.addTask(task);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return taskList;
    }
}
