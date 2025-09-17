
package nila;
import nila.parser.Parser;
import nila.storage.Storage;
import nila.tasks.TaskManager;

/**
 * The {@code Nila} class represents the main entry point of the {@code Nila} chatbot application.
 */
public class Nila {
    private Storage storage;
    private TaskManager taskList;

    /**
     * Constructs a {@code Nila} chatbot instance with the specified storage file.
     * @param filePath the path to the file where tasks are stored
     */
    public Nila(String filePath) {
        storage = new Storage(filePath);
        taskList = storage.loadTasks();
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
            return executeValidCommand(command, remaining, rawInput);
        } catch (NilaException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String executeValidCommand(Command command, String remaining, String rawInput) throws NilaException {
        switch (command) {
        case LIST:
            return taskList.listTasksAsString();
        case MARK:
            int markIndex = Parser.parseMarkIndex(remaining, taskList);
            return taskList.markDoneAsString(markIndex);
        case UNMARK:
            int unmarkIndex = Parser.parseUnmarkIndex(remaining, taskList);
            return taskList.markNotDoneAsString(unmarkIndex);
        case DELETE:
            int deleteIndex = Parser.parseDeleteIndex(remaining, taskList);
            return taskList.removeTaskAsString(deleteIndex);
        case TODO:
            return taskList.addTaskAsString(Parser.parseTodo(remaining));
        case DEADLINE:
            return taskList.addTaskAsString(Parser.parseDeadline(remaining));
        case EVENT:
            return taskList.addTaskAsString(Parser.parseEvent(remaining));
        case FIND:
            return taskList.findTasksAsString(Parser.parseFind(remaining));
        case BYE:
            return "Goodbye!";
        case UNKNOWN:
            return "Sorry, I don't understand: " + rawInput;
        default:
            return "";
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
}
