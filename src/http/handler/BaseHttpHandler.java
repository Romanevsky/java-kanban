package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import utils.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    protected final Gson gson;
    protected final TaskManager taskManager;

    public BaseHttpHandler(Gson gson, TaskManager taskManager) {
        this.gson = gson;
        this.taskManager = taskManager;
    }

    public Gson getGson() {
        return gson;
    }

    protected TaskManager getTaskManager() {
        return taskManager;
    }

    protected void sendText(HttpExchange exchange, String text, int statusCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(statusCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\": \"Resource not found\"}", 404);
    }

    protected void sendConflict(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\": \"Task overlaps with existing tasks\"}", 406);
    }

    protected void sendServerError(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\": \"Internal server error\"}", 500);
    }

    protected void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Allow", "GET, POST"); // можно изменить под нужный эндпоинт
        sendText(exchange, "{\"error\": \"Method not allowed\"}", 405);
    }
}
