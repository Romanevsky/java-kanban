package test.junit;

import entity.Status;
import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    @Test
    public void testAddSubtask() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        epic.addSubtask(subtask);
        assertEquals(1, epic.getSubtasks().size());
    }

    @Test
    public void testRemoveSubtask() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        epic.addSubtask(subtask);
        epic.removeSubtask(subtask);
        assertEquals(0, epic.getSubtasks().size());
    }

    @Test
    public void testGetSubtaskIds() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        epic.addSubtask(subtask);
        assertEquals(1, epic.getSubtaskIds().size());
    }

    @Test
    public void testCleanSubtaskIds() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        epic.addSubtask(subtask);
        epic.cleanSubtaskIds();
        assertEquals(0, epic.getSubtaskIds().size());
    }

    @Test
    public void testUpdateStatus() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        epic.addSubtask(subtask);
        epic.updateStatus();
        assertEquals(Status.NEW, epic.getStatus());
    }
}