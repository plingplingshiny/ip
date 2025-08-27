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

        File folder = new File("./data");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File("./data/nila.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating data file: " + e.getMessage());
            }
        }

        String commandStr = ui.readCommand();
        Command command;
        try {
            command = Command.valueOf(commandStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            command = Command.UNKNOWN;
        }
        String remaining = ui.readRemaining();

        TaskManager taskList = new TaskManager();
        taskList.loadTasksFromFile(file);

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
                    if (remaining.isEmpty()) {
                        throw ui.emptyTaskDescription();
                    }
                    Task curTask = new Todo(remaining);
                    taskList.addTask(curTask);
                    break;
                case DEADLINE:
                    String[] deadlineParts = remaining.split("/by", 2);
                    String description = deadlineParts[0].trim();
                    if (description.isEmpty()) {
                        throw ui.emptyTaskDescription();
                    }
                    if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                        throw new NilaException("OOPS!!! A deadline must have a /by time!");
                    }
                    String deadline = deadlineParts[1].trim();
                    Task curDeadline;
                    try {
                        LocalDateTime dt = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                        curDeadline = new Deadline(description, dt);
                    } catch (DateTimeParseException e1) {
                        try {
                            LocalDate d = LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            curDeadline = new Deadline(description, d);
                        } catch (DateTimeParseException e2) {
                            throw new NilaException("OOPS!!! Please enter the deadline in yyyy-MM-dd or yyyy-MM-dd HHmm format!");
                        }
                    }
                    taskList.addTask(curDeadline);
                    break;
                case EVENT:
                    if (!remaining.contains("/from") || !remaining.contains("/to")) {
                        throw new NilaException("OOPS!!! An event must have /from and /to timings.");
                    }
                    String[] fromParts = remaining.split("/from", 2);
                    String event = fromParts[0].trim();
                    if (event.isEmpty()) {
                        throw ui.emptyTaskDescription();
                    }
                    String[] toParts = fromParts[1].split("/to", 2);
                    if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
                        throw new NilaException("OOPS!!! An event must have both start and end timings.");
                    }
                    DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    String start = toParts[0].trim();
                    String end = toParts[1].trim();
                    try {
                        LocalDateTime startDT = LocalDateTime.parse(start, inputFmt);
                        LocalDateTime endDT = LocalDateTime.parse(end, inputFmt);
                        Task curEvent = new Event(event, startDT, endDT);
                        taskList.addTask(curEvent);
                    } catch (DateTimeParseException e) {
                        System.out.println("OOPS!!! Please enter start and end in yyyy-MM-dd HHmm format.");
                    }
                    break;
                case UNKNOWN:
                    throw ui.unknownCommandError(commandStr);
            }
            } catch (NilaException e) {
                System.out.println(e.getMessage());
            }

            ui.printLine();
            commandStr = ui.readCommand();
            try {
                command = Command.valueOf(commandStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                command = Command.UNKNOWN;
            }
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