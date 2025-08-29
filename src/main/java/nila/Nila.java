
package nila;
import nila.parser.Parser;
import nila.storage.Storage;
import nila.tasks.Task;
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
        String commandStr = ui.readCommand();
        Command command = Parser.parseCommand(commandStr);
        String remaining = ui.readRemaining();

        while (command != Command.BYE) {
            ui.printLine();
            try {
                switch (command) {
                case LIST:
                    taskList.listTasks();
                    break;
                case MARK:
                    try {
                        taskList.markDone(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        ui.invalidNum("mark");
                    }
                    break;
                case UNMARK:
                    try {
                        taskList.markNotDone(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        ui.invalidNum("unmark");
                    }
                    break;
                case DELETE:
                    try {
                        taskList.removeTask(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        ui.invalidNum("delete");
                    }
                    break;
                case TODO:
                    Task newTodo = Parser.parseTodo(remaining, ui);
                    taskList.addTask(newTodo);
                    break;
                case DEADLINE:
                    Task newDeadline = Parser.parseDeadline(remaining, ui);
                    taskList.addTask(newDeadline);
                    break;
                case EVENT:
                    Task newEvent = Parser.parseEvent(remaining, ui);
                    taskList.addTask(newEvent);
                    break;
                case UNKNOWN:
                    throw ui.unknownCommandError(commandStr);
                case FIND:
                    try {
                        String keyword = Parser.parseFind(remaining, ui);
                        taskList.findTasks(keyword);
                    } catch (NilaException e) {
                        ui.emptyKeywordError();
                    }
                    break;
                default:
                    break;
                }
                storage.saveTasks(taskList);
            } catch (NilaException e) {
                System.out.println(e.getMessage());
            }

            ui.printLine();
            commandStr = ui.readCommand();
            command = Parser.parseCommand(commandStr);
            if (command != Command.BYE) {
                remaining = ui.readRemaining();
            }
        }

        ui.showGoodbye();
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
}
