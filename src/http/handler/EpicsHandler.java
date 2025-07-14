package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.HttpTaskServer;
import model.Epic;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    private final HttpTaskServer server;

    public EpicsHandler(HttpTaskServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                if (path.equals("/epics")) {
                    List<Epic> epics = server.getTaskManager().getEpics();
                    sendText(exchange, server.getGson().toJson(epics), 200);
                } else {
                    sendNotFound(exchange);
                }
            } else if (method.equals("POST")) {
                // Реализация POST-запроса для создания эпика
                String json = readRequest(exchange);
                Epic epic = server.getGson().fromJson(json, Epic.class);
                server.getTaskManager().addNewEpic(epic);
                sendText(exchange, server.getGson().toJson(epic), 201);
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
