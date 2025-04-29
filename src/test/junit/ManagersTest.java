package test.junit;

import manager.Managers;
import org.junit.jupiter.api.Test;
import utils.HistoryManager;
import utils.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {
    @Test
    public void testGetDefault() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }

    @Test
    public void testGetDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    public void testGetDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }

    @Test
    public void testGetDefaultHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    public void testGetDefaultTaskManagerInstance() {
        TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();
        assertNotEquals(taskManager1, taskManager2);
    }
}

