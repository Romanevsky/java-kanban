package utils;

import model.Task;

import java.util.List;
import java.util.Set;

public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    void remove(Set<Integer> integers);
}

