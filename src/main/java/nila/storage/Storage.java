package nila.storage;

import java.io.File;
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
     * Constructs a Storage instance with the specified file path.
     * Creates the necessary directories and file if they do not exist.
     * @param filePath the path to the file where tasks will be stored and loaded from
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
     * Load tasks from the storage file into a new {@link TaskManager}.
     * Each link in the file is converted back into a {@link Task}.
     * @return A {@code TaskManager} containing all loaded tasks.
     */
    public TaskManager loadTasks() {
        TaskManager taskList = new TaskManager();
        try (Scanner sc = new Scanner(file)) {
            loadTasksFromScanner(sc, taskList);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return taskList;
    }

    private void loadTasksFromScanner(Scanner sc, TaskManager taskList) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            try {
                Task task = Task.fromSaveFormat(line);
                taskList.addTask(task);
            } catch (Exception e) {
                System.out.println("Error parsing task from line: " + line + " - " + e.getMessage());
            }
        }
    }
}
