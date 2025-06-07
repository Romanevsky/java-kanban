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

    @Override
    public String toString() {
        return String.format("Задача: {Id=%d," +
                " Название=%s," +
                " Описание=%s," +
                " Статус=%s" +
                "}", id, title, description, status);
    }
}

