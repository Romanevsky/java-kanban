package src.junit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Status;
import http.HttpTaskServer;
import http.adapter.DurationTypeAdapter;
import http.adapter.LocalDateTimeTypeAdapter;
import manager.Managers;
import model.Task;
import org.junit.jupiter.api.*;

import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    private static HttpTaskServer server;
    private static Gson gson;

    @BeforeAll
    public static void setUpAll() throws Exception {
        server = new HttpTaskServer(Managers.getDefault());
        server.start();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
    }

    @AfterAll
    public static void tearDownAll() {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    @DisplayName("Создание и получение задачи через HTTP")
    public void testCreateAndRetrieveTask() throws Exception {
        Task task = new Task("Test Task", "Description", 1, Status.NEW);
        String json = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();

        // POST /tasks
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, postResponse.statusCode(), "Задача не была создана");

        // GET /tasks
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode(), "Не удалось получить список задач");

        Type taskListType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(getResponse.body(), taskListType);

        assertNotNull(tasks, "Список задач не должен быть null");
        assertEquals(1, tasks.size(), "Ожидалось 1 задача");
        assertEquals("Test Task", tasks.get(0).getTitle(), "Название задачи не совпадает");
    }
}
