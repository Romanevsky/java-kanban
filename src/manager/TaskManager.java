package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class TaskManager {
    private static int id = 0;


    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, Epic> epicMap = new HashMap<>();
    public HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    //Создание новой задачи с нужным типом
    public void createNewTask(TaskType taskType, String title, String description, Integer epicPartId) {
        id = ++id;
        switch (taskType) {
            case TASK -> taskMap.put(id, new Task(title, description, id, Status.NEW));
            case EPIC -> epicMap.put(id, new Epic(title, description, id, Status.NEW));
            case SUBTASK -> subtaskMap.put(id, new Subtask(title, description, id, Status.NEW, epicPartId));
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + taskType);
        }
    }

    //Удаление списка задач по типу
    public void deletedAllTypeTasks(TaskType taskType) {
        switch (taskType) {
            case TASK -> taskMap.clear();
            case EPIC -> epicMap.clear();
            case SUBTASK -> subtaskMap.clear();
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + taskType);
        }
    }

    //Удаление задачи по id и типу
    public void deleteById(int id, TaskType type) {
        switch (type) {
            case TASK -> taskMap.remove(id);
            case EPIC -> epicMap.remove(id);
            case SUBTASK -> subtaskMap.remove(id);
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + type);
        }
    }

    //Обновление задачи по id и типу
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
                } else {
                    throw new IllegalArgumentException("Подзадача с id " + id + " не найдена");
                }
                break;
            default:
                throw new IllegalArgumentException("Не известный тип задачи: " + type);
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
