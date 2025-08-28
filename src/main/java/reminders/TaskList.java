package reminders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A list of tasks.
 */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Returns the number of tasks in the list.
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     * @param task the task
     * @return true if the task was added, false otherwise
     */
    public boolean add(Task task) {
        return tasks.add(task);
    }

    /**
     * Gets a task from the list.
     * @param index the index of the task
     * @return the task
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes a task from the list.
     * @param index the index of the task
     * @return the task
     */
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
