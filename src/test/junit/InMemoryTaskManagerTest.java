package test.junit;

import entity.Status;
import entity.TaskType;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest {

    @Test
    public void testAddNewTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    public void testAddNewEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        taskManager.addNewEpic(epic);
        assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    public void testAddNewSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        taskManager.addNewSubtask(subtask);
        assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    public void testDeleteAllTypeTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        taskManager.deleteAllTypeTasks(TaskType.TASK);
        assertEquals(0, taskManager.getTasks().size());
    }
}


