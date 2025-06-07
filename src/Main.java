import entity.Status;
import entity.TaskType;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", 2, Status.NEW);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 2, Status.NEW, 1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 3, Status.NEW, 1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", 2);

        // Добавление задач и эпиков в менеджер задач
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);

        // Добавление подзадач в менеджер задач
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        // Запрос задач и вывод истории
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getEpic(1);
        taskManager.getEpic(2);
        taskManager.getSubtask(1);
        taskManager.getSubtask(2);
        taskManager.getSubtask(3);

        System.out.println("История просмотров: " + taskManager.getHistory());

        // Удаление задачи и вывод истории
        taskManager.deleteById(1, TaskType.TASK);
        System.out.println("История просмотров после удаления задачи: " + taskManager.getHistory());

        // Удаление эпика и вывод истории
        taskManager.deleteById(1, TaskType.EPIC);
        System.out.println("История просмотров после удаления эпика: " + taskManager.getHistory());
    }
}

