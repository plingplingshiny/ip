package nila.ui;

import java.util.Scanner;

import nila.NilaException;

/**
 * aa
 */
public class UI {
    private Scanner sc;

    public UI() {
        sc = new Scanner(System.in);
    }

    /**
     * aa
     */
    public void printLine() {
        String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
    }

    /**
     * aa
     * @param name aa
     */
    public void showGreeting(String name) {
        printLine();
        System.out.println("Hello! I'm " + name + " \uD83E\uDD81");
        System.out.println("What can I do for you today?");
        printLine();
    }

    /**
     * aa
     */
    public void showGoodbye() {
        printLine();
        System.out.println("Bye!\uD83D\uDC4B Hope to see you again soon!");
        printLine();
    }

    public String readCommand() {
        return sc.next().toLowerCase();
    }

    public String readRemaining() {
        return sc.nextLine().trim();
    }

    public void close() {
        sc.close();
    }

    public void invalidNum(String command) {
        System.out.println("OOPS!!! Please enter a valid task number to " + command + "!");
    }

    public NilaException emptyTaskDescription() {
        return new NilaException("OOPS!!! Description of task cannot be empty!");
    }

    /**
     * aa
     * @param commandStr aa
     * @return aa
     */
    public NilaException unknownCommandError(String commandStr) {
        return new NilaException("Sorry, I don't know what " + commandStr
                + " means \uD83D\uDE2D\nTo add tasks, use: todo, deadline, event"
                + "\nTo see a list of your tasks, use: list"
                + "\nTo mark or unmark tasks, use: mark, unmark\n"
                + "To delete tasks, use: delete");
    }

    /**
     * Prints an empty keyword search error message to the user.
     */
    public void emptyKeywordError() {
        System.out.println("\"OOPS!!! You must provide a keyword to search for.\"");
    }

}
