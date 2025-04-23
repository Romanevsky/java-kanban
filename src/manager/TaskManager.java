package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс TaskManager управляет задачами, эпиками и подзадачами.
 */
public class TaskManager {
    private static int taskId = 0;
    private static int epicId = 0;
    private static int subtaskId = 0;

    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public int addNewTask(Task task) {
        final int id = ++taskId;
        task.setId(id);
        taskMap.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        final int id = ++epicId;
        epic.setId(id);
        epicMap.put(id, epic);
        return id;
    }

    public int addNewSubtask(Subtask subtask) {
        final int id = ++subtaskId;
        subtask.setId(id);
        subtaskMap.put(id, subtask);
        Epic epic = epicMap.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);
        }
        return id;
    }

    /**
     * Удаляет все задачи, эпики или подзадачи в зависимости от типа задачи.
     *
     * @param taskType тип задачи (TASK, EPIC, SUBTASK)
     */
    public void deleteAllTypeTasks(TaskType taskType) {
        switch (taskType) {
            case TASK -> taskMap.clear();
            case EPIC -> {
                for (Epic epic : epicMap.values()) {
                    epic.cleanSubtaskIds();
                    calculateEpicStatus(epic.getId());
                }
                epicMap.clear();
            }
            case SUBTASK -> {
                for (Subtask subtask : subtaskMap.values()) {
                    Epic epic = epicMap.get(subtask.getEpicId());
                    if (epic != null) {
                        epic.removeSubtask(subtask);
                    }
                }
                subtaskMap.clear();
            }
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
            case EPIC -> {
                Epic epic = epicMap.remove(id);
                if (epic != null) {
                    for (Subtask subtask : epic.getSubtasks()) {
                        subtaskMap.remove(subtask.getId());
                    }
                }
            }
            case SUBTASK -> {
                Subtask subtask = subtaskMap.remove(id);
                if (subtask != null) {
                    Epic epic = epicMap.get(subtask.getEpicId());
                    if (epic != null) {
                        epic.removeSubtask(subtask);
                    }
                }
            }
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + type);
        }
    }

    /**
     * Обновляет задачу, эпик или подзадачу по id.
     *
     * @param task задача, эпик или подзадача
     */
    public void updateTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        } else {
            throw new IllegalArgumentException("Задача с id " + task.getId() + " не найдена");
        }
    }

    public void updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
        } else {
            throw new IllegalArgumentException("Эпик с id " + epic.getId() + " не найден");
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            calculateEpicStatus(subtask.getEpicId());
        } else {
            throw new IllegalArgumentException("Подзадача с id " + subtask.getId() + " не найдена");
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

        for (Subtask subtask : epic.getSubtasks()) {
            hasSubtasks = true;
            if (subtask.getStatus() != Status.NEW) {
                allSubtasksNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
                allSubtasksDone = false;
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

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public Task getTask(int id) {
        return taskMap.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtaskMap.get(id);
    }

    public Epic getEpic(int id) {
        return epicMap.get(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic == null) {
            return null;
        }
        ArrayList<Subtask> tasks = new ArrayList<>();
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtaskMap.get(id));
        }
        return tasks;
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
