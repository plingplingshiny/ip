package nila.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link TaskManager} functionality
 * Specifically tests the findTasksAsString method.
 * AI-Assisted Development: This test class was created with assistance from
 * AI (deepseek) for test case design and implementation, while maintaining
 * the original code structure and functionality.
 * Date: [19 Sep 2025]
 */

public class TaskManagerTest {
    private TaskManager taskManager;
    @BeforeEach
    public void setUp() {
        taskManager = new TaskManager();
        taskManager.addTask(new Todo("Read book"));
        taskManager.addTask(new Todo("Buy groceries"));
        taskManager.addTask(new Todo("Write report"));
        taskManager.addTask(new Todo("Read newspaper"));
    }

    @Test
    public void findTasksAsString_matchingKeyword_returnsMatchingTasks() {
        String result = taskManager.findTasksAsString("book");
        assertEquals(true, result.contains("Read book"));
        assertEquals(true, result.contains("Matching tasks:"));
    }

    @Test
    public void findTasksAsString_caseInsensitiveSearch_returnsMatchingTasks() {
        String result = taskManager.findTasksAsString("BOOK");
        assertEquals(true, result.contains("Read book"));
    }

    @Test
    public void findTasksAsString_multipleMatches_returnsAllMatchingTasks() {
        String result = taskManager.findTasksAsString("read");
        assertEquals(true, result.contains("Read book"));
        assertEquals(true, result.contains("Read newspaper"));
    }

    @Test
    public void findTasksAsString_noMatches_returnsNoTasksMessage() {
        String result = taskManager.findTasksAsString("nonexistent");
        assertEquals(true, result.contains("No tasks match your search"));
    }

    @Test
    public void findTasksAsString_emptyTaskManager_returnsNoMatchingTasksMessage() {
        TaskManager emptyManager = new TaskManager();
        String result = emptyManager.findTasksAsString("book");
        assertEquals(true, result.contains("No tasks match your search"));
    }

    @Test
    public void findTasksAsString_emptyKeyword_returnsAllTasks() {
        String result = taskManager.findTasksAsString("");
        // Empty keyword should return ALL tasks (no filtering)
        assertEquals(true, result.contains("Read book"));
        assertEquals(true, result.contains("Buy groceries"));
        assertEquals(true, result.contains("Write report"));
        assertEquals(true, result.contains("Read newspaper"));
        assertEquals(true, result.contains("Matching tasks:"));
    }
}
