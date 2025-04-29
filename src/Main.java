import manager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import entity.Status;
import entity.TaskType;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManagers = new InMemoryTaskManager();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", 2, Status.NEW);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 2, Status.NEW, 1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", 2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 3, Status.NEW, 2);
        epic2.addSubtask(subtask3);

        // Добавление задач и эпиков в менеджер задач
        taskManagers.addNewTask(task1);
        taskManagers.addNewTask(task2);
        taskManagers.addNewEpic(epic1);
        taskManagers.addNewEpic(epic2);

        // Добавление подзадач в менеджер задач
        taskManagers.addNewSubtask(subtask1);
        taskManagers.addNewSubtask(subtask2);
        taskManagers.addNewSubtask(subtask3);

        // Распечатка списков эпиков, задач и подзадач
        System.out.println("Список задач: " + taskManagers.getTasks());
        System.out.println("Список эпиков: " + taskManagers.getEpics());
        System.out.println("Список подзадач: " + taskManagers.getSubtasks());

        // Изменение статусов объектов
        task1.updateStatus(Status.IN_PROGRESS);
        subtask1.updateStatus(Status.DONE);
        epic1.updateStatus();

        // Распечатка измененных объектов
        System.out.println("Задача 1: " + task1);
        System.out.println("Подзадача 1: " + subtask1);
        System.out.println("Эпик 1: " + epic1);

        // Удаление задачи и эпика
        taskManagers.deleteById(1, TaskType.TASK);
        taskManagers.deleteById(1, TaskType.EPIC);

        // Распечатка списков после удаления
        System.out.println("Список задач после удаления: " + taskManagers.getTasks());
        System.out.println("Список эпиков после удаления: " + taskManagers.getEpics());
    }
}