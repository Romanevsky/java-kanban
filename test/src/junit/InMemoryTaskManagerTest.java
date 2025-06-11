package junit;

import entity.Status;
import entity.TaskType;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testDeleteById() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        taskManager.deleteById(task.getId(), TaskType.TASK);
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    public void testUpdateTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        task.setTitle("Обновленная задача 1");
        taskManager.updateTask(task);
        assertEquals("Обновленная задача 1", taskManager.getTask(task.getId()).getTitle());
    }

    @Test
    public void testUpdateEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        taskManager.addNewEpic(epic);
        epic.setTitle("Обновленный эпик 1");
        taskManager.updateEpic(epic);
        assertEquals("Обновленный эпик 1", taskManager.getEpic(epic.getId()).getTitle());
    }

    @Test
    public void testCalculateEpicStatus() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        taskManager.addNewSubtask(subtask);
        epic.addSubtask(subtask);
        taskManager.calculateEpicStatus(epic.getId());
        assertEquals(Status.NEW, taskManager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void testGetTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        ArrayList<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.getFirst());
    }

    @Test
    public void testGetSubtasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        taskManager.addNewSubtask(subtask);
        ArrayList<Subtask> subtasks = taskManager.getSubtasks();
        assertEquals(1, subtasks.size());
        assertEquals(subtask, subtasks.getFirst());
    }

    @Test
    public void testGetEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        taskManager.addNewEpic(epic);
        ArrayList<Epic> epics = taskManager.getEpics();
        assertEquals(1, epics.size());
        assertEquals(epic, epics.getFirst());
    }

    @Test
    public void testGetTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        Task retrievedTask = taskManager.getTask(task.getId());
        assertEquals(task, retrievedTask);
    }

    @Test
    public void testGetSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        taskManager.addNewSubtask(subtask);
        Subtask retrievedSubtask = taskManager.getSubtask(subtask.getId());
        assertEquals(subtask, retrievedSubtask);
    }

    @Test
    public void testGetEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1);
        taskManager.addNewEpic(epic);
        Epic retrievedEpic = taskManager.getEpic(epic.getId());
        assertEquals(epic, retrievedEpic);
    }

    @Test
    public void testGetHistory() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        taskManager.addNewTask(task);
        taskManager.getTask(task.getId());
        List<Task> history = taskManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.getFirst());
    }
}