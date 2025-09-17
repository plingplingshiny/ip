package nila.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import nila.Nila;
import nila.NilaException;
import nila.ValidationUtils;
import nila.tasks.Deadline;
import nila.tasks.Event;
import nila.tasks.Task;
import nila.tasks.TaskManager;
import nila.tasks.Todo;


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
     * @return a {@link Todo} task
     * @throws NilaException if the description is empty
     */
    public static Task parseTodo(String args) throws NilaException {
        if (args == null || args.trim().isEmpty()) {
            throw new NilaException("OOPS!!! Description of task cannot be empty!");
        }
        return new Todo(args);
    }

    /**
     * Parses a {@link Deadline} task from user input.
     * @param args the raw string containing description and deadline
     * @return a {@link Deadline} task
     * @throws NilaException if the description is empty or the time format is invalid
     */
    public static Task parseDeadline(String args) throws NilaException {
        assert args != null : "Arguments cannot be null";
        String[] deadlineParts = validateAndSplitDeadlineArgs(args);
        String description = deadlineParts[0].trim();
        String deadline = deadlineParts[1].trim();

        ValidationUtils.validateDescription(description);

        try {
            return createDeadlineWithDateTime(description, deadline);
        } catch (DateTimeParseException e1) {
            try {
                return createDeadlineWithDate(description, deadline);
            } catch (DateTimeParseException e2) {
                throw new NilaException("OOPS!!! Please enter the deadline in yyyy-MM-dd or "
                        + "yyyy-MM-dd HHmm format!");
            }
        }
    }

    private static String[] validateAndSplitDeadlineArgs(String args) throws NilaException {
        String[] deadlineParts = args.split("/by", 2);
        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new NilaException("OOPS!!! A deadline must have a /by time!");
        }
        return deadlineParts;
    }

    private static Task createDeadlineWithDateTime(String description, String deadline) throws DateTimeParseException {
        LocalDateTime dt = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        return new Deadline(description, dt);
    }

    private static Task createDeadlineWithDate(String description, String deadline) throws DateTimeParseException {
        LocalDate d = LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new Deadline(description, d);
    }

    /**
     * Parses and {@link Event} task from user input.
     * @param args the raw string containing description, start and end times
     * @return an {link Event} task
     * @throws NilaException if description is empty or timings are missing or invalid
     */
    public static Task parseEvent(String args) throws NilaException {
        assert args != null : "Arguments cannot be null";
        validateEventFormat(args);
        String[] fromParts = args.split("/from", 2);
        String description = fromParts[0].trim();
        ValidationUtils.validateDescription(description);
        String[] toParts = fromParts[1].split("/to", 2);
        validateTimings(toParts);

        return parseEventTimings(description, toParts);
    }

    private static void validateEventFormat(String args) throws NilaException {
        if (!args.contains("/from") || !args.contains("/to")) {
            throw new NilaException("OOPS!!! An event must have both start and end timings.");
        }
    }

    private static void validateTimings(String[] toParts) throws NilaException {
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new NilaException("OOPS!!! An event must have both start and end timings");
        }
    }

    private static Task parseEventTimings(String description, String[] toParts) throws NilaException {
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            LocalDateTime startDT = LocalDateTime.parse(toParts[0].trim(), inputFmt);
            LocalDateTime endDT = LocalDateTime.parse(toParts[1].trim(), inputFmt);
            return new Event(description, startDT, endDT);
        } catch (DateTimeParseException e) {
            throw new NilaException("OOPS!!! Please enter start and end in yyyy-MM-dd HHmm format!");
        }
    }

    /**
     * Parses a find command.
     * @param args the remaining string after 'find'
     * @return the keyword to search for
     * @throws NilaException if no keyword is provided
     */
    public static String parseFind(String args) throws NilaException {
        if (args == null || args.trim().isEmpty()) {
            ValidationUtils.validateNotEmpty(args, "Search keyword");
        }
        return args;
    }

    /**
     * Parses and validates a task index for mark, unmark, and delete commands.
     * @param args the remaining string after the command
     * @param taskList the task list to validate against current task count
     * @param commandType the type of command for error message context
     * @return the 1-based parsed and validated task index
     * @throws NilaException if the input is invalid, empty or out of bounds
     */
    public static int parseTaskIndex(String args, TaskManager taskList, String commandType) throws NilaException {
        if (args == null || args.trim().isEmpty()) {
            throw new NilaException("OOPS!!! Please provide a task number to " + commandType + "!");
        }

        try {
            int index = Integer.parseInt(args.trim());
            int taskCount = taskList.getTasks().size();
            if (index < 1) {
                throw new NilaException("OOPS!!! Task number must be positive!");
            }

            if (index > taskCount) {
                if (taskCount == 0) {
                    throw new NilaException("OOPS!!! You have no tasks to " + commandType + "!");
                } else {
                    throw new NilaException("OOPS!!! Task number " + index + " does not exist!");
                }
            }
            return index;
        } catch (NumberFormatException e) {
            throw new NilaException("OOPS!!! Please provide a valid number to " + commandType + "!");
        }
    }

    /**
     * Parses and validates a task index for mark command.
     * @param args the remaining string after 'mark'
     * @param taskList the task list to validate against
     * @return the parsed and validated task index
     * @throws NilaException if the input is invalid
     */
    public static int parseMarkIndex(String args, TaskManager taskList) throws NilaException {
        return parseTaskIndex(args, taskList, "mark");
    }

    /**
     * Parses and validates a task index for unmark command.
     * @param args the remaining string after 'unmark'
     * @param taskList the task list to validate against
     * @return the parsed and validated task index
     * @throws NilaException if the input is invalid
     */
    public static int parseUnmarkIndex(String args, TaskManager taskList) throws NilaException {
        return parseTaskIndex(args, taskList, "unmark");
    }

    /**
     * Parses and validates a task index for delete command.
     * @param args the remaining string after 'delete'
     * @param taskList the task list to validate against
     * @return the parsed and validated task index
     * @throws NilaException is the input is invalid
     */
    public static int parseDeleteIndex(String args, TaskManager taskList) throws NilaException {
        return parseTaskIndex(args, taskList, "delete");
    }
}
