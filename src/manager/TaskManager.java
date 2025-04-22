package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

/**
 * Класс TaskManager управляет задачами, эпиками и подзадачами.
 */
public class TaskManager {
    private static int id = 0;

    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, Epic> epicMap = new HashMap<>();
    public HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    /**
     * Создает новую задачу, эпик или подзадачу в зависимости от типа задачи.
     *
     * @param taskType      тип задачи (TASK, EPIC, SUBTASK)
     * @param title         заголовок задачи
     * @param description   описание задачи
     * @param epicPartId    id эпика, к которому относится подзадача (для SUBTASK)
     */
    public void createNewTask(TaskType taskType, String title, String description, Integer epicPartId) {
        id = ++id;
        switch (taskType) {
            case TASK -> taskMap.put(id, new Task(title, description, id, Status.NEW));
            case EPIC -> epicMap.put(id, new Epic(title, description, id, Status.NEW));
            case SUBTASK -> subtaskMap.put(id, new Subtask(title, description, id, Status.NEW, epicPartId));
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + taskType);
        }
    }

    /**
     * Удаляет все задачи, эпики или подзадачи в зависимости от типа задачи.
     *
     * @param taskType тип задачи (TASK, EPIC, SUBTASK)
     */
    public void deletedAllTypeTasks(TaskType taskType) {
        switch (taskType) {
            case TASK -> taskMap.clear();
            case EPIC -> epicMap.clear();
            case SUBTASK -> subtaskMap.clear();
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + taskType);
        }
    }

    /**
     * Удаляет задачу, эпик или подзадачу по id.
     *
     * @param id   id задачи, эпика или подзадачи
     * @param type тип задачи (TASK, EPIC, SUBTASK)
     */
    public void deleteById(int id, TaskType type) {
        switch (type) {
            case TASK -> taskMap.remove(id);
            case EPIC -> epicMap.remove(id);
            case SUBTASK -> subtaskMap.remove(id);
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + type);
        }
    }

    /**
     * Обновляет задачу, эпик или подзадачу по id.
     *
     * @param id            id задачи, эпика или подзадачи
     * @param type          тип задачи (TASK, EPIC, SUBTASK)
     * @param title         новый заголовок задачи
     * @param description   новое описание задачи
     * @param status        новый статус задачи
     * @param epicPartId    id эпика, к которому относится подзадача (для SUBTASK)
     */
    public void updateById(int id, TaskType type, String title, String description, Status status, Integer epicPartId) {
        switch (type) {
            case TASK:
                if (taskMap.containsKey(id)) {
                    Task updatedTask = new Task(title, description, id, status);
                    taskMap.put(id, updatedTask);
                } else {
                    throw new IllegalArgumentException("Задача с id " + id + " не найдена");
                }
                break;
            case EPIC:
                if (epicMap.containsKey(id)) {
                    Epic updatedEpic = new Epic(title, description, id, status);
                    epicMap.put(id, updatedEpic);
                } else {
                    throw new IllegalArgumentException("Эпик с id " + id + " не найден");
                }
                break;
            case SUBTASK:
                if (subtaskMap.containsKey(id)) {
                    Subtask updatedSubtask = new Subtask(title, description, id, status, epicPartId);
                    subtaskMap.put(id, updatedSubtask);
                    calculateEpicStatus(updatedSubtask.getEpicId());
                } else {
                    throw new IllegalArgumentException("Подзадача с id " + id + " не найдена");
                }
                break;
            default:
                throw new IllegalArgumentException("Не известный тип задачи: " + type);
        }
    }

    /**
     * Пересчитывает статус эпика на основе статусов его подзадач.
     *
     * @param epicId id эпика
     */
    public void calculateEpicStatus(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic == null) {
            throw new IllegalArgumentException("Эпик с id " + epicId + " не найден");
        }

        boolean hasSubtasks = false;
        boolean allSubtasksNew = true;
        boolean allSubtasksDone = true;

        for (Subtask subtask : subtaskMap.values()) {
            if (subtask.getEpicId() == epicId) {
                hasSubtasks = true;
                if (subtask.getStatus() != Status.NEW) {
                    allSubtasksNew = false;
                }
                if (subtask.getStatus() != Status.DONE) {
                    allSubtasksDone = false;
                }
            }
        }

        if (!hasSubtasks || allSubtasksNew) {
            epic.updateStatus(Status.NEW);
        } else if (allSubtasksDone) {
            epic.updateStatus(Status.DONE);
        } else {
            epic.updateStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "Список задач=" + taskMap +
                ", Список эпиков=" + epicMap +
                ", Список подзадач=" + subtaskMap +
                '}';
    }
}
