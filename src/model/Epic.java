package model;

import entity.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String title, String description, int id, Status status) {
        super(title, description, id, status);
        subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        subtasks = subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }
}
