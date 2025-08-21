import java.util.Scanner;

public class Nila{
    public static void main(String[] args) throws NilaException {
        String name = "Nila";

        printLine();
        System.out.println("Hello! I'm " + name + " \uD83E\uDD81");
        System.out.println("What can I do for you today?");
        printLine();

        Scanner sc = new Scanner(System.in);
        String command = sc.next();
        String remaining = sc.nextLine().trim();

        TaskManager taskList = new TaskManager();
        while (!command.equals("bye")) {
            printLine();
            try {
                switch (command) {
                    case "list":
                        taskList.listTasks();
                        break;
                    case "mark":
                        try {
                            taskList.markDone(Integer.parseInt(remaining));
                        } catch (Exception e) {
                            System.out.println("OOPS!!! Please enter a valid task number to mark!");
                        }
                        break;
                    case "unmark":
                        try {
                            taskList.markNotDone(Integer.parseInt(remaining));
                        } catch (Exception e) {
                            System.out.println("OOPS!!! Please enter a valid task number to unmark!");
                        }
                        break;
                    case "delete":
                        try {
                            taskList.removeTask(Integer.parseInt(remaining));
                        } catch (Exception e) {
                            System.out.println("OOPS!!! Please enter a valid task number to delete!");
                        }
                        break;
                    case "todo":
                        if (remaining.isEmpty()) {
                            throw new NilaException("OOPS!!! Description of task cannot be empty!");
                        }
                        Task curTask = new Todo(remaining);
                        taskList.addTask(curTask);
                        break;
                    case "deadline":
                        String[] deadlineParts = remaining.split("/by", 2);
                        String description = deadlineParts[0].trim();
                        if (description.isEmpty()) {
                            throw new NilaException("OOPS!!! Description of task cannot be empty!");
                        }
                        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                            throw new NilaException("OOPS!!! A deadline must have a /by time!");
                        }
                        String deadline = deadlineParts[1].trim();
                        Task curDeadline = new Deadline(description, deadline);
                        taskList.addTask(curDeadline);
                        break;
                    case "event":
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
                        String start = toParts[0].trim();
                        String end = toParts[1].trim();
                        Task curEvent = new Event(event, start, end);
                        taskList.addTask(curEvent);
                        break;
                    default:
                        throw new NilaException("Sorry, I don't know what " + command + " means \uD83D\uDE2D\nTo add tasks, use: todo, deadline, event\nTo see a list of your tasks, use: list\nTo mark or unmark tasks, use: mark, unmark");
                }
            } catch (NilaException e) {
                System.out.println(e.getMessage());
            }

            printLine();
            command = sc.next();
            if (!command.equals("bye")) {
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
}
