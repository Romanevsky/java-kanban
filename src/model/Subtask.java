package model;

import entity.Status;
import entity.TaskType;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int id, Status status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void updateStatus(Status status) {
        this.setStatus(status);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%d",
                getId(), TaskType.SUBTASK, getTitle(), getStatus(), getDescription(), getEpicId());
    }
}