package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Subtask;
import utils.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {

    public SubtasksHandler(Gson gson, TaskManager taskManager) {
        super(gson, taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                if (path.equals("/subtasks")) {
                    List<Subtask> subtasks = getTaskManager().getSubtasks();
                    sendText(exchange, getGson().toJson(subtasks), 200);
                } else {
                    sendNotFound(exchange);
                }
            } else if (method.equals("POST")) {
                String json = readRequest(exchange);
                Subtask subtask = getGson().fromJson(json, Subtask.class);
                getTaskManager().addNewSubtask(subtask);
                sendText(exchange, getGson().toJson(subtask), 201);
            } else {
                sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private String readRequest(HttpExchange exchange) throws IOException {
        int length = exchange.getRequestBody().available();
        byte[] buffer = new byte[length];
        exchange.getRequestBody().read(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
