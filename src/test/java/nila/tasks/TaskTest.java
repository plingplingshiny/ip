package nila.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


public class TaskTest {

    @Test
    public void testGetIsDone() {
        Task task = new Task("read book");
        assertEquals(false, task.getIsDone(),
                "Task should be not done initially");
        task.markDone();
        assertEquals(true, task.getIsDone(),
                "Task should be done after markDone()");
        task.markNotDone();
        assertEquals(false, task.getIsDone(),
                "Task should be not done after markNotDone()");
    }

    @Test
    public void testFromSaveFormat() {
        Task todo = Task.fromSaveFormat("T|1|read book");
        assertEquals("📋 🗹 read book", todo.toString());

        Task deadline = Task.fromSaveFormat("D|0|return book|2025-09-10");
        assertEquals("❗ ☐ return book (by: Sep 10 2025)",
                deadline.toString());

        Task event = Task.fromSaveFormat("E|0|meeting|2025-09-10T14:00|2025-09-10T16:00");
        assertEquals("📅 ☐ meeting (from: Sep 10 2025 14:00 to: Sep 10 2025 16:00)",
                event.toString());
    }

    @Test
    public void testToSaveFormat() {
        // Todo
        Todo todo = new Todo("read book");
        todo.markDone();
        assertEquals("T|1|read book", todo.toSaveFormat());

        // Deadline with LocalDate
        Deadline deadline = new Deadline("return book", LocalDate.of(2025, 9, 10));
        assertEquals("D|0|return book|2025-09-10", deadline.toSaveFormat());

        // Event with LocalDateTime
        Event event = new Event("team meeting",
                LocalDateTime.of(2025, 9, 10, 14, 0),
                LocalDateTime.of(2025, 9, 10, 16, 0));
        assertEquals("E|0|team meeting|2025-09-10T14:00|2025-09-10T16:00", event.toSaveFormat());
    }
}
