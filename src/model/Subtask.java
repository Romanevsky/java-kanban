package model;

import entity.Status;
import entity.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int id, Status status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(String title, String description, int id, Status status, int epicId,
                   LocalDateTime startTime, Duration duration) {
        super(title, description, id, status, startTime, duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public void updateStatus(Status status) {
        this.setStatus(status);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,,%s,%s,%d",
                getId(), TaskType.SUBTASK, getTitle(), getStatus(), getDescription(),
                startTime != null ? startTime.toString() : "",
                duration != null ? duration.toMinutes() : 0,
                getEpicId());
    }
}
