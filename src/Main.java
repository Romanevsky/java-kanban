import entity.Status;
import entity.TaskType;
import manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Создание задач
        taskManager.createNewTask(TaskType.TASK, "Задача 1", "Описание задачи 1", null);
        taskManager.createNewTask(TaskType.TASK, "Задача 2", "Описание задачи 2", null);

        // Создание эпиков с подзадачами
        taskManager.createNewTask(TaskType.EPIC, "Эпик 1", "Описание эпика 1", null);
        taskManager.createNewTask(TaskType.SUBTASK, "Подзадача 1", "Описание подзадачи 1", 3);
        taskManager.createNewTask(TaskType.SUBTASK, "Подзадача 2", "Описание подзадачи 2", 3);

        taskManager.createNewTask(TaskType.EPIC, "Эпик 2", "Описание эпика 2", null);
        taskManager.createNewTask(TaskType.SUBTASK, "Подзадача 3", "Описание подзадачи 3", 6);

        // Распечатка списков
        System.out.println("Список задач: " + taskManager.taskMap);
        System.out.println("Список эпиков: " + taskManager.epicMap);
        System.out.println("Список подзадач: " + taskManager.subtaskMap);

        // Изменение статусов
        taskManager.updateById(1, TaskType.TASK, "Задача 1", "Описание задачи 1", Status.IN_PROGRESS, null);
        taskManager.updateById(2, TaskType.TASK, "Задача 2", "Описание задачи 2", Status.DONE, null);

        taskManager.updateById(4, TaskType.SUBTASK, "Подзадача 1", "Описание подзадачи 1", Status.DONE, 3);
        taskManager.updateById(5, TaskType.SUBTASK, "Подзадача 2", "Описание подзадачи 2", Status.IN_PROGRESS, 3);
        taskManager.updateById(7, TaskType.SUBTASK, "Подзадача 3", "Описание подзадачи 3", Status.DONE, 6);

        // Распечатка списков после изменения статусов
        System.out.println("Список задач после изменения статуса: " + taskManager.taskMap);
        System.out.println("Список эпиков после изменения статуса: " + taskManager.epicMap);
        System.out.println("Список подзадач после изменения статуса: " + taskManager.subtaskMap);

        // Проверка статусов
        System.out.println("Статус задачи 1 после изменения статуса соответствует: " + taskManager.taskMap.get(1).getStatus().equals(Status.IN_PROGRESS));
        System.out.println("Статус задачи 2 после изменения статуса соответствует: " + taskManager.taskMap.get(2).getStatus().equals(Status.DONE));

        System.out.println("Статус подзадачи 1 после изменения статуса соответствует: " + taskManager.subtaskMap.get(4).getStatus().equals(Status.DONE));
        System.out.println("Статус подзадачи 2 после изменения статуса соответствует: " + taskManager.subtaskMap.get(5).getStatus().equals(Status.IN_PROGRESS));
        System.out.println("Статус подзадачи 3 после изменения статуса соответствует: " + taskManager.subtaskMap.get(7).getStatus().equals(Status.DONE));

        System.out.println("Статус эпика 1 после изменения статуса соответствует: " + taskManager.epicMap.get(3).getStatus().equals(Status.NEW));
        System.out.println("Статус эпика 2 после изменения статуса соответствует: " + taskManager.epicMap.get(6).getStatus().equals(Status.NEW));

        // Удаление задачи и эпика
        taskManager.deleteById(1, TaskType.TASK);
        taskManager.deleteById(3, TaskType.EPIC);

        // Распечатка списков после удаления
        System.out.println("Список задач: " + taskManager.taskMap);
        System.out.println("Список эпиков: " + taskManager.epicMap);
        System.out.println("Список подзадач: " + taskManager.subtaskMap);
    }
}
