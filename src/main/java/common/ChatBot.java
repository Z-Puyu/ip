package common;

import comments.CommentContext;
import comments.CommentTopic;
import inputs.InputCommand;
import reminders.EmptyTaskException;
import reminders.TaskList;
import reminders.Task;
import reminders.UndefinedDeadlineException;
import reminders.UndefinedTimeFrameException;

import java.io.IOException;

/**
 * A chatbot that can be used to interact with the user.
 */
public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final TaskList taskList = new TaskList();

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    /**
     * Prints a message to the console.
     * @param text the message
     */
    public void say(String text) {
        System.out.println(ChatBot.SEPARATOR + '\n' + text.trim() + '\n' + ChatBot.SEPARATOR);
    }

    /**
     * Marks a task as done.
     * @param index the index of the task
     */
    public void markTask(int index) {
        if (index < 1 || index > this.taskList.size()) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = this.taskList.get(index - 1);
            task.complete();
            this.say(this.config.FetchComment(CommentTopic.TaskIsDone, CommentContext.OfTask(task, index)));
        }
    }

    /**
     * Marks a task as not done.
     * @param index the index of the task
     */
    public void unmarkTask(int index) {
        if (index < 1 || index > this.taskList.size()) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = this.taskList.get(index - 1);
            task.reset();
            this.say(this.config.FetchComment(CommentTopic.TaskIsReset, CommentContext.OfTask(task, index)));
        }
    }

    /**
     * Lists all tasks on the console.
     * The tasks are numbered.
     */
    public void denumerateTasks() {
        // TODO: What to say if there are no tasks?
        this.say(this.config.FetchComment(CommentTopic.ListingTask, CommentContext.OfMemo(this.taskList, null)));
    }

    /**
     * Adds a task to the list.
     * @param task the task
     * @return true if the task was added, false otherwise
     */
    public boolean addTask(Task task) {
        return this.taskList.add(task);
    }

    /**
     * Creates a task from an input command.
     * @param command the input command
     */
    public void createTask(InputCommand command) {
        try {
            Task task = Task.from(command);
            if (this.addTask(task)) {
                this.say(this.config.FetchComment(CommentTopic.AddTask, CommentContext.OfMemo(this.taskList, task)));
            }
        } catch (EmptyTaskException e) {
            this.say(this.config.FetchComment(CommentTopic.TaskWithoutDescription, CommentContext.OfTask(null, -1)));
        } catch (UndefinedDeadlineException e) {
            this.say(this.config.FetchComment(CommentTopic.UndefinedDeadline, CommentContext.OfTask(null, -1)));
        } catch (UndefinedTimeFrameException e) {
            this.say(this.config.FetchComment(CommentTopic.UndefinedEventTime, CommentContext.OfTask(null, -1)));
        }
    }

    /**
     * Prints the bot's logo to the console.
     */
    public void showLogo() {
        System.out.println(this.config.getLogo() + '\n' + ChatBot.SEPARATOR);
    }

    /**
     * Prints the greeting to the console.
     */
    public void greetUser() {
        System.out.println(this.config.getGreeting() + ChatBot.SEPARATOR);
    }

    /**
     * Prints the farewell to the console.
     */
    public void sayGoodbye() throws IOException {
        Storage.save(this.taskList);
        System.out.println(ChatBot.SEPARATOR + '\n' + this.config.getFarewell());
    }

    /**
     * Prints an alert to the console.
     * @param command the input command
     */
    public void alert(InputCommand command) {
        this.say(this.config.FetchComment(CommentTopic.UndefinedCommand, CommentContext.OfCommand(command)));
    }

    @Override
    public String toString() {
        return this.config.getName();
    }

    /**
     * Deletes a task from the list.
     * @param index the index of the task
     */
    public void deleteTask(int index) {
        Task removed = this.taskList.removeAt(index);
        if (removed == null) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, index)));
        } else {
            this.say(this.config.FetchComment(CommentTopic.RemoveTask, CommentContext.OfTask(removed, index)));
        }
    }
}
