package manager;

import entity.Status;
import entity.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;
import utils.ManagerSaveException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    private int taskID;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        // Создание временного файла для сохранения данных
        File file = null;
        try {
            file = File.createTempFile("tasks", ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Создание менеджера задач
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

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
        // Загрузка данных из файла
        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(file);

        // Проверка сохранения задач, эпиков и подзадач
        System.out.println("Задачи:");
        for (Task task : loadedTaskManager.getTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : loadedTaskManager.getEpics()) {
            System.out.println(epic);
        }

        System.out.println("Подзадачи:");
        for (Subtask subtask : loadedTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("Подзадачи эпика с идентификатором 1:");
        System.out.println(loadedTaskManager.getEpic(1).getSubtasks());
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            // Записываем заголовок CSV файла
            writer.write("id,type,name,status,description,startTime,duration,epic\n");

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
        // Создаем новый менеджер задач
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                if (line.startsWith("id,type,name,status,description,startTime,duration,epic")) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields[1].equals("TASK")) {
                    Task task;
                    if (fields.length >= 7 && !fields[5].isEmpty()) {
                        LocalDateTime startTime = LocalDateTime.parse(fields[5]);
                        Duration duration = Duration.ofMinutes(Long.parseLong(fields[6]));
                        task = new Task(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0]),
                                Status.valueOf(fields[3]),
                                startTime,
                                duration
                        );
                    } else {
                        task = new Task(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0]),
                                Status.valueOf(fields[3])
                        );
                    }
                    taskManager.taskMap.put(task.getId(), task);
                    if (task.getId() > taskManager.taskID) {
                        taskManager.taskID = task.getId();
                    }
                } else if (fields[1].equals("EPIC")) {
                    Epic epic;
                    if (fields.length >= 8 && !fields[5].isEmpty()) {
                        LocalDateTime startTime = LocalDateTime.parse(fields[5]);
                        Duration duration = Duration.ofMinutes(Long.parseLong(fields[6]));
                        epic = new Epic(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0])
                        );
                        epic.setStartTime(startTime);
                        epic.setDuration(duration);

                        // Восстанавливаем подзадачи
                        if (fields.length > 7 && !fields[7].isEmpty()) {
                            String[] subtaskIdsStr = fields[7].split(",");
                            for (String subtaskIdStr : subtaskIdsStr) {
                                int subtaskId = Integer.parseInt(subtaskIdStr);
                                Subtask subtask = taskManager.subtaskMap.get(subtaskId);
                                if (subtask != null) {
                                    epic.addSubtask(subtask);
                                }
                            }
                        }
                    } else {
                        epic = new Epic(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0])
                        );
                    }
                    taskManager.epicMap.put(epic.getId(), epic);
                    if (epic.getId() > taskManager.taskID) {
                        taskManager.taskID = epic.getId();
                    }
                } else if (fields[1].equals("SUBTASK")) {
                    Subtask subtask;
                    if (fields.length >= 8 && !fields[5].isEmpty()) {
                        LocalDateTime startTime = LocalDateTime.parse(fields[5]);
                        Duration duration = Duration.ofMinutes(Long.parseLong(fields[6]));
                        subtask = new Subtask(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0]),
                                Status.valueOf(fields[3]),
                                Integer.parseInt(fields[7]),
                                startTime,
                                duration
                        );
                    } else {
                        subtask = new Subtask(
                                fields[2],
                                fields[4],
                                Integer.parseInt(fields[0]),
                                Status.valueOf(fields[3]),
                                Integer.parseInt(fields[7])
                        );
                    }
                    taskManager.subtaskMap.put(subtask.getId(), subtask);
                    if (subtask.getId() > taskManager.taskID) {
                        taskManager.taskID = subtask.getId();
                    }
                    // Добавляем подзадачу в соответствующий эпик
                    Epic epic = taskManager.epicMap.get(subtask.getEpicId());
                    if (epic != null) {
                        epic.addSubtask(subtask);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке данных из файла", e);
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

    @Override
    public String toString() {
        return "FileBackedTaskManager{" +
                "file=" + file +
                ", taskMap=" + taskMap +
                ", epicMap=" + epicMap +
                ", subtaskMap=" + subtaskMap +
                '}';
    }
}