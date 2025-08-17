import java.util.Scanner;

public class Nila {
    public static void main(String[] args) {
        String name = "Nila";

        printLine();
        System.out.println("Hello! I'm " + name + " :)");
        System.out.println("What can I do for you today?");
        printLine();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            printLine();
            Echo output = new Echo(input);
            output.toEcho();
            printLine();
            input = sc.nextLine();
        }

        System.out.println("Bye! Hope to see you again soon!");
        printLine();

        sc.close();
    }

    public static void printLine() {
        String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
    }
}
