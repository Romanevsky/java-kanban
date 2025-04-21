import entity.TaskType;
import manager.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        TaskManager taskManager2 = new TaskManager();
        taskManager.createNewTask(TaskType.TASK, "First Task", "First Task Description", null);
        taskManager2.createNewTask(TaskType.TASK, "Second Task", "Second Task Description", null);

        System.out.println(taskManager.taskMap.toString());

    }
}
