package reminders;

import java.util.ArrayList;
import java.util.List;

public class Memo {
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
}
