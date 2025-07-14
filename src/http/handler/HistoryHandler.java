package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.HttpTaskServer;
import model.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final HttpTaskServer server;

    public HistoryHandler(HttpTaskServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                List<Task> history = server.getTaskManager().getHistory();
                sendText(exchange, server.getGson().toJson(history), 200);
            } else {
                sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }
}
