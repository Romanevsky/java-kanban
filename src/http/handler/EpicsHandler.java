package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Epic;
import utils.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {

    public EpicsHandler(Gson gson, TaskManager taskManager) {
        super(gson, taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                if (path.equals("/epics")) {
                    List<Epic> epics = taskManager.getEpics();
                    sendText(exchange, gson.toJson(epics), 200);
                } else {
                    sendNotFound(exchange);
                }
            } else if (method.equals("POST")) {
                String json = readRequest(exchange);
                Epic epic = gson.fromJson(json, Epic.class);
                taskManager.addNewEpic(epic);
                sendText(exchange, gson.toJson(epic), 201);
            } else {
                sendMethodNotAllowed(exchange);
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