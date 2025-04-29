package test.junit;

import entity.Status;
import manager.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    @Test
    public void testAdd() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }
}