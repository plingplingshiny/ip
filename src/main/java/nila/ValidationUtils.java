package nila;

/**
 * Utility class for common validation methods used across the {@code Parser} class.
 */
public class ValidationUtils {

    /**
     * Validates that a task description is not null or empty.
     * @param description the description to validate
     * @throws NilaException if the description is null or empty
     */
    public static void validateDescription(String description) throws NilaException {
        if (description == null || description.trim().isEmpty()) {
            throw new NilaException("OOPS!!! Description of task cannot be empty!");
        }
    }

    /**
     * Validates that a string is not null of empty.
     * @param input the string to validate
     * @param fieldName the name of the field for error messages
     * @throws NilaException if the input is null or empty
     */
    public static void validateNotEmpty(String input, String fieldName) throws NilaException {
        if (input == null || input.trim().isEmpty()) {
            throw new NilaException("OOPS!!! " + fieldName + " cannot be empty!");
        }
    }
}
