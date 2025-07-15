package http.handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.TaskType;
import model.Task;
import utils.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {

    public TasksHandler(Gson gson, TaskManager taskManager) {
        super(gson, taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    handleGetTasks(exchange, path);
                    break;
                case "POST":
                    handlePostTask(exchange);
                    break;
                case "DELETE":
                    handleDeleteTask(exchange, path);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetTasks(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/tasks")) {
            List<Task> tasks = getTaskManager().getTasks();
            sendText(exchange, getGson().toJson(tasks), 200);
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        String json = readRequest(exchange);
        try {
            Task task = getGson().fromJson(json, Task.class);
            getTaskManager().addNewTask(task);
            sendText(exchange, getGson().toJson(task), 201);
        } catch (JsonSyntaxException e) {
            sendServerError(exchange);
        }
    }

    private void handleDeleteTask(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/tasks")) {
            getTaskManager().deleteAllTypeTasks(TaskType.TASK);
            sendText(exchange, "Tasks deleted", 200);
        } else {
            sendNotFound(exchange);
        }
    }

    private String readRequest(HttpExchange exchange) throws IOException {
        int length = exchange.getRequestBody().available();
        byte[] buffer = new byte[length];
        exchange.getRequestBody().read(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
