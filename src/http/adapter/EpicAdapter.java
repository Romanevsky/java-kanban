package http.adapter;

import entity.Status;
import entity.TaskType;
import model.Epic;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class EpicAdapter extends TypeAdapter<Epic> {
    @Override
    public void write(JsonWriter out, Epic epic) throws IOException {
        out.beginObject();
        out.name("id").value(epic.getId());
        out.name("title").value(epic.getTitle());
        out.name("description").value(epic.getDescription());
        out.name("status").value(epic.getStatus().name());
        out.name("type").value(epic.getType().name());
        out.endObject();
    }

    @Override
    public Epic read(JsonReader in) throws IOException {
        int id = 0;
        String title = null;
        String description = null;
        Status status = Status.NEW;
        TaskType type = TaskType.EPIC;

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

        return new Epic(title, description, id);
    }
}
