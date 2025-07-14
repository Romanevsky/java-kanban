package model;

import entity.Status;
import entity.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private int id;
    protected Status status;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = TaskType.TASK;
    }

    public Task(String title, String description, int id, Status status,
                LocalDateTime startTime, Duration duration) {
        this(title, description, id, status);
        this.startTime = startTime;
        this.duration = duration;
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

    public TaskType getType() {
        return type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,,%s,%s",
                getId(), TaskType.TASK, getTitle(), getStatus(), getDescription(),
                startTime != null ? startTime.toString() : "",
                duration != null ? duration.toMinutes() : 0);
    }
}
