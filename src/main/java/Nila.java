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

        printLine();
        System.out.println("Hello! I'm " + name + " \uD83E\uDD81");
        System.out.println("What can I do for you today?");
        printLine();


        Scanner sc = new Scanner(System.in);

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

        String commandStr = sc.next().toLowerCase();
        Command command;
        try {
            command = Command.valueOf(commandStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            command = Command.UNKNOWN;
        }
        String remaining = sc.nextLine().trim();

        TaskManager taskList = new TaskManager();
        taskList.loadTasksFromFile(file);

        while (command != Command.BYE) {
            printLine();
            try {
                switch (command) {
                case LIST:
                    taskList.listTasks();
                    break;
                case MARK:
                    try {
                        taskList.markDone(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        System.out.println("OOPS!!! Please enter a valid task number to mark!");
                    }
                    break;
                case UNMARK:
                    try {
                        taskList.markNotDone(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        System.out.println("OOPS!!! Please enter a valid task number to unmark!");
                    }
                    break;
                case DELETE:
                    try {
                        taskList.removeTask(Integer.parseInt(remaining));
                    } catch (Exception e) {
                        System.out.println("OOPS!!! Please enter a valid task number to delete!");
                    }
                    break;
                case TODO:
                    if (remaining.isEmpty()) {
                        throw new NilaException("OOPS!!! Description of task cannot be empty!");
                    }
                    Task curTask = new Todo(remaining);
                    taskList.addTask(curTask);
                    break;
                case DEADLINE:
                    String[] deadlineParts = remaining.split("/by", 2);
                    String description = deadlineParts[0].trim();
                    if (description.isEmpty()) {
                        throw new NilaException("OOPS!!! Description of task cannot be empty!");
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
                        throw new NilaException("OOPS!!! The description of an event cannot be empty.");
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
                    throw new NilaException("Sorry, I don't know what " + commandStr + " means \uD83D\uDE2D\nTo add tasks, use: todo, deadline, event\nTo see a list of your tasks, use: list\nTo mark or unmark tasks, use: mark, unmark");
            }
            } catch (NilaException e) {
                System.out.println(e.getMessage());
            }

            printLine();
            commandStr = sc.next().toLowerCase();
            try {
                command = Command.valueOf(commandStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                command = Command.UNKNOWN;
            }
            if (command != Command.BYE) {
                remaining = sc.nextLine().trim();
            }
        }

        System.out.println("Bye!\uD83D\uDC4B Hope to see you again soon!");
        printLine();

        sc.close();
    }

    public static void printLine() {
        String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
    }
    public enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, BYE, UNKNOWN
    }
}