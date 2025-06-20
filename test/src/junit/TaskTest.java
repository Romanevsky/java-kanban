package src.junit;

import entity.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testSetTitle() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        task.setTitle("Обновленная задача 1");
        assertEquals("Обновленная задача 1", task.getTitle());
    }

    @Test
    public void testSetDescription() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        task.setDescription("Обновленное описание задачи 1");
        assertEquals("Обновленное описание задачи 1", task.getDescription());
    }

    @Test
    public void testSetId() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        task.setId(2);
        assertEquals(2, task.getId());
    }

    @Test
    public void testSetStatus() {
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        task.setStatus(Status.DONE);
        assertEquals(Status.DONE, task.getStatus());
    }
}
