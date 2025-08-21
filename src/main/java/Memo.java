import java.util.*;

public class Memo implements List<Task> {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.tasks.contains(o);
    }

    @Override
    public Iterator<Task> iterator() {
        return this.tasks.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.tasks.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.tasks.toArray(a);
    }

    @Override
    public boolean add(Task task) {
        return this.tasks.add(task);
    }

    @Override
    public boolean remove(Object o) {
        return this.tasks.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.tasks.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Task> c) {
        return this.tasks.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Task> c) {
        return this.tasks.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.tasks.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.tasks.retainAll(c);
    }

    @Override
    public void clear() {
        this.tasks.clear();
    }

    @Override
    public Task get(int index) {
        return this.tasks.get(index);
    }

    @Override
    public Task set(int index, Task element) {
        return this.tasks.set(index, element);
    }

    @Override
    public void add(int index, Task element) {
        this.tasks.add(index, element);
    }

    @Override
    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.tasks.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.tasks.lastIndexOf(o);
    }

    @Override
    public ListIterator<Task> listIterator() {
        return this.tasks.listIterator();
    }

    @Override
    public ListIterator<Task> listIterator(int index) {
        return this.tasks.listIterator(index);
    }

    @Override
    public List<Task> subList(int fromIndex, int toIndex) {
        return this.tasks.subList(fromIndex, toIndex);
    }
}
