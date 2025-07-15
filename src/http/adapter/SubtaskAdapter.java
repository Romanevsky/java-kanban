package http.adapter;

import entity.Status;
import entity.TaskType;
import model.Subtask;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class SubtaskAdapter extends TypeAdapter<Subtask> {
    @Override
    public void write(JsonWriter out, Subtask subtask) throws IOException {
        out.beginObject();
        out.name("id").value(subtask.getId());
        out.name("title").value(subtask.getTitle());
        out.name("description").value(subtask.getDescription());
        out.name("status").value(subtask.getStatus().name());
        out.name("type").value(subtask.getType().name());
        out.name("epicId").value(subtask.getEpicId());
        out.endObject();
    }

    @Override
    public Subtask read(JsonReader in) throws IOException {
        int id = 0;
        String title = null;
        String description = null;
        Status status = Status.NEW;
        TaskType type = TaskType.SUBTASK;
        int epicId = 0;

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
                case "epicId":
                    epicId = in.nextInt();
                    break;
            }
        }
        in.endObject();

        return new Subtask(title, description, id, status, epicId);
    }
}
