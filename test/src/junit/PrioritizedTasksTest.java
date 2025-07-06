package src.junit;

import entity.Status;
import manager.InMemoryTaskManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrioritizedTasksTest {

    @Test
    public void testGetPrioritizedTasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Задача 1", "Описание 1", 1, Status.NEW,
                LocalDateTime.of(2023, 1, 1, 10, 0), Duration.ofMinutes(60));
        Task task2 = new Task("Задача 2", "Описание 2", 2, Status.NEW,
                LocalDateTime.of(2023, 1, 1, 9, 0), Duration.ofMinutes(60));

        manager.addNewTask(task1);
        manager.addNewTask(task2);

        List<Task> prioritized = manager.getPrioritizedTasks();
        assertEquals(2, prioritized.size());
        assertEquals(task2, prioritized.get(0));
        assertEquals(task1, prioritized.get(1));
    }

    @Test
    public void testCheckIntersection() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Задача 1", "Описание 1", 1, Status.NEW,
                LocalDateTime.of(2023, 1, 1, 10, 0), Duration.ofMinutes(60));
        Task task2 = new Task("Задача 2", "Описание 2", 2, Status.NEW,
                LocalDateTime.of(2023, 1, 1, 10, 30), Duration.ofMinutes(60));

        manager.addNewTask(task1);
        assertTrue(manager.checkIntersection(task2));
    }
}
