import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Nila{
    public static void main(String[] args) throws NilaException {
        String name = "Nila";
        UI ui = new UI();
        ui.showGreeting(name);

        Storage storage = new Storage("./data/nila.txt");
        TaskManager taskList = storage.loadTasks();

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
            } storage.saveTasks(taskList);
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

    public enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, BYE, UNKNOWN
    }
}