
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
     * Returns a help manual for GUI display
     * AI-Assisted Improvements: This help manual was refined with
     * assistance from AI for improved formatting, readability, and user experience.
     * Content and functionality remain original work.
     * Date: [17 Sep 2025]
     * @return a help manual listing all commands, their uses and format
     */
    public String displayHelp() {
        return """
               \u2753 HELP MANUAL \u2753\n
               %s
               %s
               %s
               %s
               %s
               %s
               %s
               %s
               """.formatted(displayListHelp(),
                displayTaskHelp(),
                displayMarkHelp(),
                displayUnmarkHelp(),
                displayDeleteHelp(),
                displayFindHelp(),
                displayByeHelp(),
                displayHelpCommand()
        );
    }

    private String displayListHelp() {
        return """
                📋 LIST
                 • shows a list of tasks added\n
                Format: list\n
                """;
    }

    private String displayTaskHelp() {
        return """
                📝 TASKS
                 • we have 3 different task types: Todo, Deadline, Event\n
                ✅ TODO
                 • adds a todo task without any date/time attached to it\n
                Format: todo <description>
                Example: todo read book\n
                ⏰ DEADLINE
                 • adds a deadline task that needs to be done before a specific date/time\n
                Format:
                 • deadline <description> /by <yyyy-MM-dd>
                 • deadline <description> /by <yyyy-MM-dd HHmm>
                Example: deadline return book /by 2025-09-09 1400\n
                🎉 EVENT
                 • adds an event task that start at a specific date/time and ends at a specific date/time\n
                Format:
                 • event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>
                 • event <description /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
                Example: event party /from 2025-12-12 1700 /to 2025-12-12 2100\n
                """;
    }

    private String displayMarkHelp() {
        return """
                ✔ MARK
                 • mark a task as done using its respective index number in the list of tasks\n
                Format: mark <index>
                Example: mark 1\n
                """;
    }

    private String displayUnmarkHelp() {
        return """
                🔄 UNMARK
                 • unmark a task as not done using its respective index number in the list of tasks\n
                Format: unmark <index>
                Example: unmark 2\n
                """;
    }

    private String displayDeleteHelp() {
        return """
                🗑 DELETE
                 • delete a task from the list of tasks using its respective index number\n
                Format: delete <index>
                Example: delete 1\n
                """;
    }

    private String displayFindHelp() {
        return """
                🔍 FIND
                 • find a task by searching for a keyword in the task description\n
                Format: find <keyword>
                Example: find book\n
                """;
    }

    private String displayByeHelp() {
        return """
                👋 BYE
                 • exit and close chatbot\n
                Format: bye\n
                """;
    }

    private String displayHelpCommand() {
        return """
                ❓ HELP
                 • display this help manual\n
                Format: help
                """;
    }
}
