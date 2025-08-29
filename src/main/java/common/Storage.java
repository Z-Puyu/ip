package common;

import reminders.TaskList;
import reminders.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A storage for tasks. Manages serialisation and deserialisation.
 */
public final class Storage {
    private static final Path DATA_DIR = Paths.get("src", "main", "data");
    private static final Path SAVE_FILE = DATA_DIR.resolve("tasks.dat");

    private Storage() { }

    public static void save(TaskList taskList) throws IOException {
        if (Files.notExists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(
                        SAVE_FILE,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE
                )
        )) {
            // Write the number of tasks, then each task
            oos.writeInt(taskList.size());
            for (Task task : taskList) {
                oos.writeObject(task);
            }

            oos.flush();
        }
    }

    private static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        if (Files.notExists(SAVE_FILE)) {
            return tasks;
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(SAVE_FILE))) {
            int count = ois.readInt();
            for (int i = 0; i < count; i += 1) {
                tasks.add((Task) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            return List.of();
        }

        return tasks;
    }

    public static void load(ChatBot bot) {
        for (Task task : Storage.loadTasks()) {
            bot.addTask(task);
        }
    }
}
