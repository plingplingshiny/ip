package nila.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import nila.Nila;
import nila.NilaException;
import nila.tasks.Deadline;
import nila.tasks.Event;
import nila.tasks.Task;
import nila.tasks.Todo;
import nila.ui.UI;

/**
 * The {@code Parser} class is responsible for interpreting user input
 * strings into commands and task objects like {@link Todo}, {@link Deadline}
 * and {@link Event}. It also validates input formats and throws
 * {@link NilaException} for invalid inputs.
 */
public class Parser {

    /**
     * Parses the command word from user input and converts it into a
     * {@link Nila.Command}. If the command word is not recognized,
     * {@link Nila.Command#UNKNOWN} is returned.
     * @param input input the raw user input string
     * @return the parsed {@link Nila.Command}
     */
    public static Nila.Command parseCommand(String input) {
        String[] parts = input.trim().split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        try {
            return Nila.Command.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return Nila.Command.UNKNOWN;
        }
    }

    /**
     * Parses a {@link Todo} task from user input.
     * @param args the description of the todo
     * @param ui the UI instance used to provide error messages
     * @return a {@link Todo} task
     * @throws NilaException if the description is empty
     */
    public static Task parseTodo(String args, UI ui) throws NilaException {
        if (args.isEmpty()) {
            throw ui.emptyTaskDescription();
        }
        return new Todo(args);
    }

    /**
     * Parses a {@link Deadline} task from user input.
     * @param args the raw string containing description and deadline
     * @param ui the UI instance used to provide error messages
     * @return a {@link Deadline} task
     * @throws NilaException if the description is empty or the time format is invalid
     */
    public static Task parseDeadline(String args, UI ui) throws NilaException {
        String[] deadlineParts = args.split("/by", 2);
        String description = deadlineParts[0].trim();
        if (description.isEmpty()) {
            throw ui.emptyTaskDescription();
        }
        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new NilaException("OOPS!!! A deadline must have a /by time!");
        }
        String deadline = deadlineParts[1].trim();
        try {
            LocalDateTime dt = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            return new Deadline(description, dt);
        } catch (DateTimeParseException e1) {
            try {
                LocalDate d = LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new Deadline(description, d);
            } catch (DateTimeParseException e2) {
                throw new NilaException("OOPS!!! Please enter the deadline in yyyy-MM-dd or"
                        + "yyyy-MM-dd HHmm format!");
            }
        }
    }

    /**
     * Parses and {@link Event} task from user input.
     * @param args the raw string containing description, start and end times
     * @param ui the UI instance used to provide error messages
     * @return an {link Event} task
     * @throws NilaException if description is empty or timings are missing or invalid
     */
    public static Task parseEvent(String args, UI ui) throws NilaException {
        if (!args.contains("/from") || !args.contains("/to")) {
            throw new NilaException("OOPS!!! An event must have /from and /to timings.");
        }
        String[] fromParts = args.split("/from", 2);
        String description = fromParts[0].trim();
        if (description.isEmpty()) {
            throw ui.emptyTaskDescription();
        }
        String[] toParts = fromParts[1].split("/to", 2);
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new NilaException("OOPS!!! An event must have both start and end timings.");
        }
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            LocalDateTime startDT = LocalDateTime.parse(toParts[0].trim(), inputFmt);
            LocalDateTime endDT = LocalDateTime.parse(toParts[1].trim(), inputFmt);
            return new Event(description, startDT, endDT);
        } catch (DateTimeParseException e) {
            throw new NilaException("OOPS!!! Please enter start and end in yyyy-MM-dd HHmm format.");
        }
    }
}
