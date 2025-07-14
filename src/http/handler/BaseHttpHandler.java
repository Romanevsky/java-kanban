package http.handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
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
}
