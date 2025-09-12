
package nila;
import nila.parser.Parser;
import nila.storage.Storage;
import nila.tasks.TaskManager;
import nila.ui.UI;

/**
 * The {@code Nila} class represents the main entry point of the {@code Nila} chatbot application.
 */
public class Nila {
    private Storage storage;
    private TaskManager taskList;
    private UI ui;

    /**
     * Constructs a {@code Nila} chatbot instance with the specified storage file.
     * @param filePath the path to the file where tasks are stored
     */
    public Nila(String filePath) {
        ui = new UI();
        storage = new Storage(filePath);
        ui.showGreeting("Nila");
        taskList = storage.loadTasks();
        ui.printLine();
    }

    /**
     * Runs the main loop of the {@code Nila} chatbot
     */
    public void run() {
        ui.showGreeting("Nila");
        String input = ui.readCommand() + " " + ui.readRemaining();

        while (!input.equalsIgnoreCase("bye")) {
            ui.printLine();
            String response = getResponse(input);
            System.out.println(response);
            ui.printLine();

            input = ui.readCommand() + " " + ui.readRemaining();
        }

        ui.showGoodbye(this);
        ui.close();
    }


    public static void main(String[] args) throws NilaException {
        new Nila("./data/nila.txt").run();
    }

    /**
     * Represents the different types of commands supported by the {@code Nila} chatbot
     */
    public enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, BYE, UNKNOWN, FIND
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String commandWord = input.contains(" ") ? input.split(" ", 2)[0] : input;
        String remaining = input.contains(" ") ? input.split(" ", 2)[1] : "";
        Command command = Parser.parseCommand(commandWord);
        return executeCommand(command, remaining, input);
    }


    private String executeCommand(Command command, String remaining, String rawInput) {
        try {
            switch (command) {
            case LIST:
                return taskList.listTasksAsString();
            case MARK:
                return taskList.markDoneAsString(Integer.parseInt(remaining));
            case UNMARK:
                return taskList.markNotDoneAsString(Integer.parseInt(remaining));
            case DELETE:
                return taskList.removeTaskAsString(Integer.parseInt(remaining));
            case TODO:
                return taskList.addTaskAsString(Parser.parseTodo(remaining, ui));
            case DEADLINE:
                return taskList.addTaskAsString(Parser.parseDeadline(remaining, ui));
            case EVENT:
                return taskList.addTaskAsString(Parser.parseEvent(remaining, ui));
            case FIND:
                return taskList.findTasksAsString(Parser.parseFind(remaining, ui));
            case BYE:
                return "Goodbye!";
            case UNKNOWN:
                return "Sorry, I don't understand: " + rawInput;
            default:
                return "";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /** Returns the greeting message for GUI display */
    public String getGreeting() {
        return "Hello! I'm Nila \uD83E\uDD81\nWhat can I do for you today?\n";
    }

    /**
     * Returns the goodbye message for GUI display
     * @return String goodbye message
     */
    public String getGoodbye() {
        return "Bye!\uD83D\uDC4B Hope to see you again soon!";
    }

    /**
     * Load tasks from the storage file into a new {@link TaskManager}.
     */
    public void loadTasksFromStorage() {
        taskList = storage.loadTasks();
    }

    /**
     * Saves all tasks from the {@link TaskManager} into the storage file.
     */
    public void saveTasksToStorage() {
        storage.saveTasks(taskList);
    }
}
