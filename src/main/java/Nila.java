import java.util.Scanner;

public class Nila {
    public static void main(String[] args) {
        String horizontalLine = "____________________________________________________________";
        String name = "Nila";

        System.out.println(horizontalLine);
        System.out.println("Hello! I'm " + name + " :)");
        System.out.println("What can I do for you today?");
        System.out.println(horizontalLine);

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            Echo output = new Echo(input);
            output.toEcho();
            System.out.println(horizontalLine);
            input = sc.nextLine();
        }

        System.out.println("Bye! Hope to see you again soon!");
        System.out.println(horizontalLine);

        sc.close();
    }
}
