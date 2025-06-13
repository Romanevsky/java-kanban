package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;
import utils.ManagerSaveException;
import utils.TaskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", 2, Status.NEW);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 2, Status.NEW, 1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 3, Status.NEW, 1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", 2);

        // Создание файла для сохранения данных
        File file = new File("tasks.csv");

        // Создание менеджера задач
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        // Добавление задач и эпиков в менеджер задач
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);

        // Добавление подзадач в менеджер задач
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        // Сохранение данных в файл
        taskManager.save();

        // Создание нового менеджера задач из файла
        FileBackedTaskManager newTaskManager = FileBackedTaskManager.loadFromFile(file);

        // Проверка сохранения задач, эпиков и подзадач
        System.out.println("Задачи:");
        for (Task task : newTaskManager.getTasks()) {
            System.out.println(task);
        }

    }


    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            // Записываем заголовок CSV файла
            writer.write("id,type,name,status,description,epic\n");

            // Записываем задачи
            List<Task> tasks = getTasks();
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }

            // Записываем эпики
            List<Epic> epics = getEpics();
            for (Epic epic : epics) {
                writer.write(epic.toString() + "\n");
            }

            // Записываем подзадачи
            List<Subtask> subtasks = getSubtasks();
            for (Subtask subtask : subtasks) {
                writer.write(subtask.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных в файл", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                if (!line.startsWith("id,")) {
                    Task task = TaskManager.fromString(line);
                    switch (task.getType()) {
                        case TASK:
                            taskManager.addNewTask(task);
                            break;
                        case EPIC:
                            taskManager.addNewEpic((Epic) task);
                            break;
                        case SUBTASK:
                            Subtask subtask = (Subtask) task;
                            taskManager.addNewSubtask(subtask);
                            Epic epic = taskManager.getEpic(subtask.getEpicId());
                            if (epic != null) {
                                epic.addSubtask(subtask);
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении данных из файла", e);
        }

        return taskManager;
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void deleteAllTypeTasks(TaskType taskType) {
        super.deleteAllTypeTasks(taskType);
        save();
    }

    @Override
    public void deleteById(int id, TaskType type) {
        super.deleteById(id, type);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void calculateEpicStatus(int epicId) {
        super.calculateEpicStatus(epicId);
        save();
    }
}
