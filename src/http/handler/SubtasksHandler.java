package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.HttpTaskServer;
import model.Subtask;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private final HttpTaskServer server;

    public SubtasksHandler(HttpTaskServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                if (path.equals("/subtasks")) {
                    List<Subtask> subtasks = server.getTaskManager().getSubtasks();
                    sendText(exchange, server.getGson().toJson(subtasks), 200);
                } else {
                    sendNotFound(exchange);
                }
            } else if (method.equals("POST")) {
                // Реализация POST-запроса для создания подзадачи
                String json = readRequest(exchange);
                Subtask subtask = server.getGson().fromJson(json, Subtask.class);
                server.getTaskManager().addNewSubtask(subtask);
                sendText(exchange, server.getGson().toJson(subtask), 201);
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
