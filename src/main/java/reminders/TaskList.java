package reminders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TaskList implements Iterable<Task> {
    private final List<Task> tasks = new ArrayList<>();

    public int size() {
        return tasks.size();
    }

    public boolean add(Task task) {
        return tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task removeAt(int index) {
        return tasks.remove(index);
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    @Override
    public void forEach(Consumer<? super Task> action) {
        tasks.forEach(action);
    }

    @Override
    public Spliterator<Task> spliterator() {
        return tasks.spliterator();
    }
}
