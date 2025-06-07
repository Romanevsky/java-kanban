package utils;

import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int addNewTask(Task task);
    int addNewEpic(Epic epic);
    int addNewSubtask(Subtask subtask);
    void deleteAllTypeTasks(TaskType taskType);
    void deleteById(int id, TaskType type);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);
    void calculateEpicStatus(int epicId);
    ArrayList<Task> getTasks();
    ArrayList<Subtask> getSubtasks();
    ArrayList<Epic> getEpics();
    Task getTask(int id);
    Subtask getSubtask(int id);
    Epic getEpic(int id);
    ArrayList<Subtask> getEpicSubtasks(int epicId);
    List<Task> getHistory();
}
