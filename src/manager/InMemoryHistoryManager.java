package manager;

import model.Task;
import utils.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history = new LinkedList<>();
    private final HashMap<Integer, Node> taskNodes = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        Node node = taskNodes.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        history.addLast(task);
        node = new Node();
        node.task = task;
        if (last != null) {
            last.next = node;
            node.prev = last;
        }
        last = node;
        if (first == null) {
            first = node;
        }
        taskNodes.put(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        Node node = taskNodes.get(id);
        if (node != null) {
            removeNode(node);
            taskNodes.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        history.remove(node.task);
    }

    public static class Node {
        Task task;
        Node prev;
        Node next;
    }
}
