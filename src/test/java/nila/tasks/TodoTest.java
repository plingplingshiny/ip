package nila.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void testConstructor_andToString_notDone() {
        Todo todo = new Todo("read book");
        String expected = "\uD83D\uDCCB ☐ read book";
        assertEquals(expected, todo.toString(),
                "Todo toString() not matching for not-done task");
    }

    @Test
    public void testMarkDone_andToString_done() {
        Todo todo = new Todo("read book");
        todo.markDone();
        String expected = "📋 🗹 read book"; // 🗹 is \uD83D\uDDF9
        assertEquals(expected, todo.toString(),
                "Todo toString() not matching for done task");
    }

    @Test
    public void testToSaveFormat_notDone() {
        Todo todo = new Todo("read book");
        assertEquals("T|0|read book", todo.toSaveFormat());
    }

    @Test
    public void testToSaveFormat_done() {
        Todo todo = new Todo("read book");
        todo.markDone();
        assertEquals("T|1|read book", todo.toSaveFormat());
    }
}
