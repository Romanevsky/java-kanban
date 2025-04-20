package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class TaskManager {
    private static int epicId = 0;
    private static int taskId = 0;
    private static int subtaskId = 0;

    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public void createNewTask(TaskType taskType, String title, String description) {
        int id = 0;
        switch (taskType) {
            case TASK -> {
                id = taskId++;
                taskMap.put(id, new Task(title, description, id, Status.NEW));
            }
            case EPIC -> {
                id = epicId++;
                epicMap.put(id, new Epic(title, description, id, Status.NEW));
            }
            case SUBTASK -> {
                id = subtaskId++;
                subtaskMap.put(id, new Subtask(title, description, id, Status.NEW));

            }
            default -> {
                throw new RuntimeException("Не известный тип задачи");
            }

        }
    }


}
