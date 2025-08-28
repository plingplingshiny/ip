package nila.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


public class DeadlineTest {
    @Test
    public void testDeadlineToStringAndSaveFormat_dateOnly() {
        Deadline deadline = new Deadline("return book",
                LocalDate.of(2025, 9, 10));
        assertEquals("❗ ☐ return book (by: Sep 10 2025)", deadline.toString());
        assertEquals("D|0|return book|2025-09-10", deadline.toSaveFormat());
        deadline.markDone();
        assertEquals("❗ 🗹 return book (by: Sep 10 2025)", deadline.toString());
        assertEquals("D|1|return book|2025-09-10", deadline.toSaveFormat());
    }

    @Test
    public void testDeadlineToStringAndSaveFormat_dateTime() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2025, 9, 10, 18, 30));
        assertEquals("❗ ☐ submit report (by: Sep 10 2025 18:30)", deadline.toString());
        assertEquals("D|0|submit report|2025-09-10T18:30", deadline.toSaveFormat());
        deadline.markDone();
        assertEquals("❗ 🗹 submit report (by: Sep 10 2025 18:30)", deadline.toString());
        assertEquals("D|1|submit report|2025-09-10T18:30", deadline.toSaveFormat());
    }
}
