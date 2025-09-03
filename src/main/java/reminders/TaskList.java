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
     * @return true if the task was added, false otherwise
     */
    public boolean add(Task task) {
        return tasks.add(task);
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
     * @param index the index of the task
     * @return the task
     */
    public Task removeAt(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a list of tasks that match the given predicate.
     *
     * @param predicate the predicate
     * @return the list of tasks
     */
    public List<Task> where(Predicate<Task> predicate) {
        if (predicate == null) {
            return this.tasks;
        }

        List<Task> results = new ArrayList<>();
        for (Task task : this.tasks) {
            if (predicate.test(task)) {
                results.add(task);
            }
        }

        return results;
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
