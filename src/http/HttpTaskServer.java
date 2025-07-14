package http;

import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.adapter.EpicAdapter;
import http.adapter.SubtaskAdapter;
import http.adapter.TaskAdapter;
import http.handler.*;
import manager.InMemoryTaskManager;
import manager.Managers;
import model.Epic;
import model.Subtask;
import model.Task;
import utils.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpTaskServer {
    private final HttpServer server;
    private final Gson gson;
    private final InMemoryTaskManager taskManager;

    public static final int PORT = 8080;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = (InMemoryTaskManager) taskManager;
        this.server = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Task.class, new TaskAdapter())
                .registerTypeAdapter(Subtask.class, new SubtaskAdapter())
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .create();

        // Регистрация обработчиков
        server.createContext("/tasks", new TasksHandler(this));
        server.createContext("/subtasks", new SubtasksHandler(this));
        server.createContext("/epics", new EpicsHandler(this));
        server.createContext("/history", new HistoryHandler(this));
        server.createContext("/prioritized", new PrioritizedHandler(this));
    }

    public void start() {
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public void stop() {
        server.stop(0);
        System.out.println("Server stopped");
    }

    public Gson getGson() {
        return gson;
    }

    public InMemoryTaskManager getTaskManager() {
        return taskManager;
    }

    public static void main(String[] args) throws IOException {
        InMemoryTaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        new HttpTaskServer(manager).start();
    }
}
