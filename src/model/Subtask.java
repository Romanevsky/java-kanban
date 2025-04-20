package model;

import entity.Status;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description, int id, Status status, Epic epic) {
        super(title, description, id, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epic=" + epic +
                '}';
    }
}
