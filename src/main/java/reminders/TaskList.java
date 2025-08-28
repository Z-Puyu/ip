package reminders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TaskList implements Iterable<Task> {
    private final List<Task> tasks = new ArrayList<>();

    public int size() {
        return this.tasks.size();
    }

    public boolean add(Task task) {
        return this.tasks.add(task);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public Task removeAt(int index) {
        return this.tasks.remove(index);
    }

    public List<Task> where(Predicate<Task> predicate) {
        if (predicate == null) {
            return this.tasks;
        }

        List<Task> result = new ArrayList<>();
        for (Task task : this.tasks) {
            if (predicate.test(task)) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public Iterator<Task> iterator() {
        return this.tasks.iterator();
    }

    @Override
    public void forEach(Consumer<? super Task> action) {
        this.tasks.forEach(action);
    }

    @Override
    public Spliterator<Task> spliterator() {
        return this.tasks.spliterator();
    }
}
