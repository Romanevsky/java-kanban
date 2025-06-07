package junit;

import entity.Status;
import model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubtaskTest {
    @Test
    public void testUpdateStatus() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        subtask.updateStatus(Status.DONE);
        assertEquals(Status.DONE, subtask.getStatus());
    }

    @Test
    public void testGetEpicId() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        assertEquals(1, subtask.getEpicId());
    }

    @Test
    public void testNotEquals() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 2, Status.NEW, 1);
        assertNotEquals(subtask1, subtask2);
    }

    @Test
    public void testToString() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        assertEquals("Subtask{epicId=1, id=1, name='Подзадача 1', description='Описание подзадачи 1', status=NEW}", subtask.toString());
    }
}