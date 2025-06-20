package model;

import entity.Status;
import entity.TaskType;
import manager.InMemoryHistoryManager;

public class Task extends InMemoryHistoryManager.Node {
    private String title;
    private String description;
    private int id;
    private Status status;
    private TaskType type;

    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = TaskType.TASK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public Task fromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        TaskType type = TaskType.valueOf(values[1]);
        String title = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];

        switch (type) {
            case TASK:
                return new Task(title, description, id, status);
            case EPIC:
                return new Epic(title, description, id);
            case SUBTASK:
                int epicId = Integer.parseInt(values[5]);
                return new Subtask(title, description, id, status, epicId);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,",
                getId(), TaskType.TASK, getTitle(), getStatus(), getDescription());
    }
}

