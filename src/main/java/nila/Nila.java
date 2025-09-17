
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
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, BYE, UNKNOWN, FIND, HELP
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
        assert command != null : "Command cannot be null";
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
        case HELP:
            return displayHelp();
        case UNKNOWN:
            return "Sorry, I don't understand: " + rawInput;
        default:
            return "";
        }
    }

    /**
     * Returns the greeting message for GUI display
     */
    public String getGreeting() {
        return "Hello! I'm Nila \uD83E\uDD81\n"
                + "What can I do for you today?\n"
                + "Type 'help' for more info!";
    }

    /**
     * Returns the goodbye message for GUI display
     * @return String goodbye message
     */
    public String getGoodbye() {
        return "Bye!\uD83D\uDC4B Hope to see you again soon!";
    }

    /**
     * Returns a help message for GUI display
     * @return a help manual listing all the commands, their uses and format
     */
    public String displayHelp() {
        StringBuilder sb = new StringBuilder("\u2753HELP MANUAL\n");
        sb.append(displayListHelp()).append(displayTaskHelp());
        sb.append(displayMarkHelp()).append(displayUnmarkHelp()).append(displayDeleteHelp());
        sb.append(displayFindHelp()).append(displayByeHelp());
        sb.append("HELP\ndisplay this help manual\n\n"
                + "Format: help");
        return sb.toString();
    }

    private String displayListHelp() {
        return "\nLIST\n • shows a list of tasks added\n\n"
                + "Format: list\n\n";
    }

    private String displayTaskHelp() {
        return "TASKS\n • we have 3 different task types: Todo, Deadline, Event\n\n"
                + "TODO\n • adds a todo task without any date/time attached to it\n\n"
                + "Format:\n • todo <description>\n ➡ e.g. todo read book\n\n"
                + "DEADLINE\n • adds a deadline task that needs to be done before a specific date/time\n\n"
                + "Format:\n • deadline <description> /by <yyyy-MM-dd>\n"
                + " • deadline <description> /by <yyyy-MM-dd HHmm>\n"
                + " ➡ e.g. deadline return book /by 2025-09-09 1400\n\n"
                + "EVENT\n • adds an event task that start at a specific date/time and ends at a specific date/time\n\n"
                + "Format:\n • event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>\n"
                + " • event <description /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                + " ➡ e.g. event party /from 2025-12-12 1700 /to 2025-12-12 2100\n\n";
    }

    private String displayMarkHelp() {
        return "MARK\n • mark a task as done using its respective index number in the list of tasks\n\n"
                + "Format:\n • mark <index>\n ➡ e.g. mark 1\n\n";
    }

    private String displayUnmarkHelp() {
        return "UNMARK\n • unmark a task as not done using its respective index number in the list of tasks\n\n"
                + "Format:\n • unmark <index>\n ➡ e.g. unmark 2\n\n";
    }

    private String displayDeleteHelp() {
        return "DELETE\n • delete a task from the list of tasks using its respective index number\n\n"
                + "Format:\n • delete <index>\n ➡ e.g. delete 1\n\n";
    }

    private String displayFindHelp() {
        return "FIND\n • find a task by searching for a keyword in the task description\n\n"
                + "Format:\n • find <keyword>\n ➡ e.g. find book\n\n";
    }

    private String displayByeHelp() {
        return "BYE\n • exit and close chatbot\n\n"
                + "Format: bye\n\n";
    }
}
