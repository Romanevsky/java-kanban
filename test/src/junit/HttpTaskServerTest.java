package src.junit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Status;
import http.HttpTaskServer;
import manager.Managers;
import model.Task;
import org.junit.jupiter.api.*;

import java.lang.reflect.Type;
import java.net.*;
import java.net.http.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    private static HttpTaskServer server;
    private static Gson gson;

    @BeforeAll
    public static void setUpAll() throws Exception {
        server = new HttpTaskServer(Managers.getDefault());
        server.start();
        gson = server.getGson();
    }

    @AfterAll
    public static void tearDownAll() {
        server.stop();
    }

    @Test
    public void testCreateAndRetrieveTask() throws Exception {
        Task task = new Task("Test Task", "Description", 1, Status.NEW);
        String json = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(response.body(), taskListType);

        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }
}
