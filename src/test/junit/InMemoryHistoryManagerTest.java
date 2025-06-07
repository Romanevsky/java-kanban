package test.junit;

import entity.Status;
import manager.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    @Test
    public void testAdd() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    public void testRemove() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    public void testGetHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.getFirst());
    }
}