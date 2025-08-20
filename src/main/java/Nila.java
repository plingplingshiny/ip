import java.util.Scanner;

public class Nila {
    public static void main(String[] args) {
        String name = "Nila";

        printLine();
        System.out.println("Hello! I'm " + name + " \uD83E\uDD81");
        System.out.println("What can I do for you today?");
        printLine();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] parts = input.split("\\s+", 2);
        String command = parts[0];
        String remaining = (parts.length > 1) ? parts[1] : "";

        TaskManager taskList = new TaskManager();
        while (!command.equals("bye")) {
            printLine();
            if (command.equals("list")) {
                taskList.listTasks();
            } else {
                Task curTask = new Task(command + " " + remaining);
                taskList.addTask(curTask);
            }
            printLine();
            input = sc.nextLine();
            parts = input.split("\\s+", 2);
            command = parts[0];
            remaining = (parts.length > 1) ? parts[1] : "";
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
