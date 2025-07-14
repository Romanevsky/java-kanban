package http.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Task;

import java.io.IOException;

public class TaskAdapter extends TypeAdapter<Task> {
    @Override
    public void write(JsonWriter out, Task task) throws IOException {
        out.beginObject();
        out.name("id").value(task.getId());
        out.name("title").value(task.getTitle());
        out.name("description").value(task.getDescription());
        out.name("status").value(task.getStatus().name());
        out.name("type").value(task.getType().name());
        out.endObject();
    }

    @Override
    public Task read(JsonReader in) throws IOException {
        int id = 0;
        String title = null;
        String description = null;
        Status status = Status.NEW;
        TaskType type = TaskType.TASK;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    id = in.nextInt();
                    break;
                case "title":
                    title = in.nextString();
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "status":
                    status = Status.valueOf(in.nextString());
                    break;
                case "type":
                    type = TaskType.valueOf(in.nextString());
                    break;
            }
        }
        in.endObject();

        return switch (type) {
            case TASK -> new Task(title, description, id, status);
            case EPIC -> new Epic(title, description, id);
            case SUBTASK -> throw new UnsupportedOperationException("Need epic ID for subtask");
        };
    }
}
