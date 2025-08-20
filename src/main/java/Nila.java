import java.util.Scanner;

public class Nila {
    public static void main(String[] args) {
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
            switch (command) {
                case "list":
                    taskList.listTasks();
                    break;
                case "mark":
                    taskList.markDone(Integer.parseInt(remaining));
                    break;
                case "unmark":
                    taskList.markNotDone(Integer.parseInt(remaining));
                    break;
                case "todo":
                    Task curTask = new Todo(remaining);
                    taskList.addTask(curTask);
                    break;
                case "deadline":
                    String[] deadlineParts = remaining.split("/by", 2);
                    String description = deadlineParts[0].trim();
                    String deadline = (deadlineParts.length > 1) ? deadlineParts[1].trim() : "";
                    Task curDeadline = new Deadline(description, deadline);
                    taskList.addTask(curDeadline);
                    break;
                case "event":
                    String[] fromParts = remaining.split("/from", 2);
                    String event = fromParts[0].trim();
                    String[] toParts = fromParts[1].split("/to", 2);
                    String start = toParts[0].trim();
                    String end = toParts[1].trim();
                    Task curEvent = new Event(event, start, end);
                    taskList.addTask(curEvent);
                    break;
            }

            printLine();
            command = sc.next();
            remaining = sc.nextLine().trim();
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
