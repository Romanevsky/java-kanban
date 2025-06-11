package manager;

import model.Task;
import utils.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> taskNodes = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        Node node = taskNodes.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
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
        List<Task> history = new ArrayList<>();
        Node current = first;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    @Override
    public void remove(Set<Integer> integers) {

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
    }

    public static class Node {
        Task task;
        Node prev;
        Node next;
    }
}
