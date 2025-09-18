package reminders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A list of tasks.
 */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task
     */
    public void add(Task task) {
        if (tasks.contains(task)) {
            return;
        }

        tasks.add(task);
    }

    /**
     * Checks if the list contains a task.
     * @param task the task
     * @return true if the list contains the task, false otherwise
     */
    public boolean contains(Task task) {
        return tasks.contains(task);
    }

    /**
     * Gets a task from the list.
     *
     * @param index the index of the task
     * @return the task
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes a task from the list.
     *
     * @param index the 1-based index of the task
     * @return the task
     */
    public Task removeAt(int index) {
        if (index < 1 || index > tasks.size()) {
            return null;
        }

        return tasks.remove(index - 1);
    }

    /**
     * Returns a list of tasks that match the given predicate.
     *
     * @param predicate the predicate
     * @return the list of tasks
     */
    public List<Task> where(Predicate<Task> predicate) {
        if (predicate == null) {
            return new ArrayList<>(tasks);
        }

        List<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (predicate.test(task)) {
                results.add(task);
            }
        }

        return results;
    }

    @Override
    public Iterator<Task> iterator() {
        return where(null).iterator();
    }

    @Override
    public void forEach(Consumer<? super Task> action) {
        tasks.forEach(action);
    }

    @Override
    public Spliterator<Task> spliterator() {
        return where(null).spliterator();
    }
}
