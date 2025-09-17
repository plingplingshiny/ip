package nila.ui;

import java.util.Scanner;

import nila.Nila;
import nila.NilaException;

/**
 * Handles interactions with the user, including displaying messages,
 * reading input, and reporting errors
 */
public class UI {
    private Scanner sc;

    public UI() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints a horizontal divider line.
     */
    public void printLine() {
        String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
    }

    /**
     * Displays a greeting message to the user when the program starts.
     * @param name the name of the chatbot
     */
    public void showGreeting(String name) {
        printLine();
        System.out.println("Hello! I'm " + name + " \uD83E\uDD81");
        System.out.println("What can I do for you today?");
        printLine();
    }

    /**
     * Displays a goodbye message to the user when the program ends.
     */
    public void showGoodbye(Nila nila) {
        printLine();
        System.out.println(nila.getGoodbye());
        printLine();
    }

    /**
     * Reads the next command word entered by the user.
     * @return the command string in lower case
     */
    public String readCommand() {
        return sc.next().toLowerCase();
    }

    /**
     * Reads the remainder of the user input after the command word.
     * @return the remaining string entered by the user
     */
    public String readRemaining() {
        return sc.nextLine().trim();
    }

    /**
     * Closes the scanner used for reading user input
     */
    public void close() {
        sc.close();
    }

    /**
     * Prints an error message when the user provides an invalid task number.
     * @param command the command that failed
     */
    public void invalidNum(String command) {
        System.out.println("OOPS!!! Please enter a valid task number to " + command + "!");
    }

    /**
     * Returns an exception indicating that the user attempted to create
     * a task without a description.
     * @return a (@code NilaException) with the error message
     */
    public NilaException emptyTaskDescription() {
        return new NilaException("OOPS!!! Description of task cannot be empty!");
    }

    /**
     * Returns an exception indicating that the user entered an unknown command.
     * @param commandStr the invalid command entered by the user
     * @return a {@code NilaException} with the error message
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
