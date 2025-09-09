package common;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import comments.CommentContext;
import comments.CommentTopic;
import inputs.InputCommand;
import reminders.EmptyTaskException;
import reminders.Task;
import reminders.TaskList;
import reminders.UndefinedDeadlineException;
import reminders.UndefinedTimeFrameException;

/**
 * The chatbot class.
 */
public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final TaskList taskList = new TaskList();
    private final Consumer<String> onOutput;

    /**
     * Creates a chatbot.
     * @param config the chatbot config
     * @param onOutput the output consumer
     */
    public ChatBot(ChatBotConfig config, Consumer<String> onOutput) {
        this.config = config;
        this.onOutput = onOutput;
    }

    /**
     * Prints a message to the console.
     *
     * @param text the message
     */
    public void say(String text) {
        assert onOutput != null;
        onOutput.accept(text.trim());
        System.out.println(ChatBot.SEPARATOR + '\n' + text.trim() + '\n' + ChatBot.SEPARATOR);
    }

    /**
     * Marks a task as done.
     *
     * @param index the index of the task
     */
    public void markTask(int index) {
        if (index < 1 || index > taskList.size()) {
            say(config.fetchComment(CommentTopic.InvalidTask, CommentContext.ofTask(null, -1)));
        } else {
            Task task = taskList.get(index - 1);
            task.complete();
            say(config.fetchComment(CommentTopic.TaskIsDone, CommentContext.ofTask(task, index)));
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param index the index of the task
     */
    public void unmarkTask(int index) {
        if (index < 1 || index > taskList.size()) {
            say(config.fetchComment(CommentTopic.InvalidTask, CommentContext.ofTask(null, -1)));
        } else {
            Task task = taskList.get(index - 1);
            task.reset();
            say(config.fetchComment(CommentTopic.TaskIsReset, CommentContext.ofTask(task, index)));
        }
    }

    /**
     * Lists all tasks on the console.
     * The tasks are numbered.
     *
     * @param predicate a filtering condition for tasks
     */
    public void denumerateTasks(Predicate<Task> predicate) {
        // TODO: What to say if there are no tasks?

        this.say(config.fetchComment(CommentTopic.ListingTask,
                CommentContext.ofTaskList(taskList.where(predicate), null, taskList.size())));
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task
     * @return true if the task was added, false otherwise
     */
    public boolean addTask(Task task) {
        return taskList.add(task);
    }

    /**
     * Creates a task from an input command.
     *
     * @param command the input command
     */
    public void createTask(InputCommand command) {
        try {
            Task task = Task.from(command);
            if (addTask(task)) {
                int size = taskList.size();
                say(config.fetchComment(CommentTopic.AddTask, CommentContext.ofTaskList(taskList, task, size)));
            }
        } catch (EmptyTaskException e) {
            say(config.fetchComment(CommentTopic.TaskWithoutDescription, CommentContext.ofTask(null, -1)));
        } catch (UndefinedDeadlineException e) {
            say(config.fetchComment(CommentTopic.UndefinedDeadline, CommentContext.ofTask(null, -1)));
        } catch (UndefinedTimeFrameException e) {
            say(config.fetchComment(CommentTopic.UndefinedEventTime, CommentContext.ofTask(null, -1)));
        }
    }

    /**
     * Prints the bot's logo to the console.
     */
    public void showLogo() {
        System.out.println(config.getLogo() + '\n' + ChatBot.SEPARATOR);
    }

    /**
     * Prints the greeting to the console.
     */
    public void greetUser() {
        assert onOutput != null;
        onOutput.accept(config.getGreeting());
        System.out.println(config.getGreeting() + ChatBot.SEPARATOR);
    }

    /**
     * Prints the farewell to the console.
     */
    public void sayGoodbye() throws IOException {
        Storage.save(taskList);
        assert onOutput != null;
        onOutput.accept(config.getFarewell());
        System.out.println(ChatBot.SEPARATOR + '\n' + config.getFarewell());
    }

    /**
     * Prints an alert to the console.
     *
     * @param command the input command
     */
    public void alert(InputCommand command) {
        say(config.fetchComment(CommentTopic.UndefinedCommand, CommentContext.ofCommand(command)));
    }

    @Override
    public String toString() {
        return config.getName();
    }

    /**
     * Deletes a task from the list.
     *
     * @param index the index of the task
     */
    public void deleteTask(int index) {
        Task removed = taskList.removeAt(index);
        if (removed == null) {
            say(config.fetchComment(CommentTopic.InvalidTask, CommentContext.ofTask(null, index)));
        } else {
            say(config.fetchComment(CommentTopic.RemoveTask, CommentContext.ofTask(removed, index)));
        }
    }
}
