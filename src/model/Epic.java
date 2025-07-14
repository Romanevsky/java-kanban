package model;

import entity.Status;
import entity.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
        subtasks = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        subtaskIds.add(subtask.getId());
        updateFields();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        subtaskIds.remove((Integer) subtask.getId());
        updateFields();
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    private void updateFields() {
        if (subtasks.isEmpty()) {
            startTime = null;
            endTime = null;
            duration = Duration.ZERO;
            return;
        }

        startTime = subtasks.stream()
                .map(Task::getStartTime)
                .filter(java.util.Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        endTime = subtasks.stream()
                .map(Task::getEndTime)
                .filter(java.util.Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        duration = subtasks.stream()
                .map(Task::getDuration)
                .filter(java.util.Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,,%s,%s,%s",
                getId(),
                TaskType.EPIC,
                getTitle(),
                getStatus(),
                getDescription(),
                startTime != null ? startTime.toString() : "",
                duration != null ? duration.toMinutes() : 0,
                subtaskIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("")
        );
    }
}
