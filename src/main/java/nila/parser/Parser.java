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
 * aa
 */
public class Parser {

    /**
     * aa
     * @param input aa
     * @return aa
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
     * aa
     * @param args aa
     * @param ui aa
     * @return aa
     * @throws NilaException aa
     */
    public static Task parseTodo(String args, UI ui) throws NilaException {
        if (args.isEmpty()) {
            throw ui.emptyTaskDescription();
        }
        return new Todo(args);
    }

    /**
     * aa
     * @param args aa
     * @param ui aa
     * @return aa
     * @throws NilaException aa
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
                throw new NilaException("OOPS!!! Please enter the deadline in yyyy-MM-dd or yyyy-MM-dd HHmm format!");
            }
        }
    }

    /**
     * aa
     * @param args aa
     * @param ui aa
     * @return aa
     * @throws NilaException aa
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
