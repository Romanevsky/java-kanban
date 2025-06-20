package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;
import utils.HistoryManager;
import utils.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected static int taskId = 0;
    protected static int epicId = 0;
    protected static int subtaskId = 0;

    protected final HashMap<Integer, Task> taskMap = new HashMap<>();
    protected final HashMap<Integer, Epic> epicMap = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int addNewTask(Task task) {
        final int id = ++taskId;
        task.setId(id);
        taskMap.put(id, task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++epicId;
        epic.setId(id);
        epicMap.put(id, epic);
        return id;
    }

    @Override
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

    @Override
    public void deleteAllTypeTasks(TaskType taskType) {
        switch (taskType) {
            case TASK -> {
                taskMap.clear();
                historyManager.remove(taskMap.keySet());
            }
            case EPIC -> {
                for (Epic epic : epicMap.values()) {
                    epic.cleanSubtaskIds();
                    calculateEpicStatus(epic.getId());
                }
                epicMap.clear();
                historyManager.remove(epicMap.keySet());
            }
            case SUBTASK -> {
                for (Subtask subtask : subtaskMap.values()) {
                    Epic epic = epicMap.get(subtask.getEpicId());
                    if (epic != null) {
                        epic.removeSubtask(subtask);
                    }
                }
                subtaskMap.clear();
                historyManager.remove(subtaskMap.keySet());
            }
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + taskType);
        }
    }


    @Override
    public void deleteById(int id, TaskType type) {
        switch (type) {
            case TASK -> {
                taskMap.remove(id);
                historyManager.remove(id);
            }
            case EPIC -> {
                Epic epic = epicMap.remove(id);
                if (epic != null) {
                    for (Subtask subtask : epic.getSubtasks()) {
                        subtaskMap.remove(subtask.getId());
                        historyManager.remove(subtask.getId());
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
                    historyManager.remove(id);
                }
            }
            default -> throw new IllegalArgumentException("Не известный тип задачи: " + type);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        } else {
            throw new IllegalArgumentException("Задача с id " + task.getId() + " не найдена");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
        } else {
            throw new IllegalArgumentException("Эпик с id " + epic.getId() + " не найден");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            calculateEpicStatus(subtask.getEpicId());
        } else {
            throw new IllegalArgumentException("Подзадача с id " + subtask.getId() + " не найдена");
        }
    }

    @Override
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

        if (!hasSubtasks) {
            epic.updateStatus(Status.NEW);
        } else if (allSubtasksNew) {
            epic.updateStatus(Status.NEW);
        } else if (allSubtasksDone) {
            epic.updateStatus(Status.DONE);
        } else {
            epic.updateStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()) {
            if (task.getType() == TaskType.TASK) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public Task getTask(int id) {
        Task task = taskMap.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtaskMap.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epicMap.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }
        ArrayList<Subtask> tasks = new ArrayList<>();
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtaskMap.get(id));
        }
        return tasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
