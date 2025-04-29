package test.junit;

import entity.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    @Test
    public void testNotEquals() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", 2, Status.NEW);
        assertNotEquals(task1, task2);
    }

    @Test
    public void testGetTitle() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        assertEquals("Задача 1", task.getTitle());
    }

    @Test
    public void testGetDescription() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        assertEquals("Описание задачи 1", task.getDescription());
    }

    @Test
    public void testGetId() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        assertEquals(1, task.getId());
    }
}