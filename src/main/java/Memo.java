import java.util.HashMap;
import java.util.Map;

public class Memo {
    private final Map<String, Task> tasks = new HashMap<>();

    public boolean add(Task task) {
        if (task == null || this.tasks.containsKey(task.name())) {
            return false;
        }

        this.tasks.put(task.name(), task);
        return true;
    }
}
