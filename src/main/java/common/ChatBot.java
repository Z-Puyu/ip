package common;

import java.io.IOException;

import comments.CommentContext;
import comments.CommentTopic;
import inputs.InputCommand;
import reminders.EmptyTaskException;
import reminders.Task;
import reminders.TaskList;
import reminders.UndefinedDeadlineException;
import reminders.UndefinedTimeFrameException;

public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final TaskList taskList = new TaskList();

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    public void say(String text) {
        System.out.println(ChatBot.SEPARATOR + '\n' + text.trim() + '\n' + ChatBot.SEPARATOR);
    }

    public void markTask(int index) {
        if (index < 1 || index > taskList.size()) {
            say(config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = taskList.get(index - 1);
            task.complete();
            say(config.FetchComment(CommentTopic.TaskIsDone, CommentContext.OfTask(task, index)));
        }
    }

    public void unmarkTask(int index) {
        if (index < 1 || index > taskList.size()) {
            say(config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = taskList.get(index - 1);
            task.reset();
            say(config.FetchComment(CommentTopic.TaskIsReset, CommentContext.OfTask(task, index)));
        }
    }

    public void denumerateTasks() {
        // TODO: What to say if there are no tasks?
        say(config.FetchComment(CommentTopic.ListingTask, CommentContext.OfMemo(taskList, null)));
    }

    public boolean addTask(Task task) {
        return taskList.add(task);
    }

    public void createTask(InputCommand command) {
        try {
            Task task = Task.from(command);
            if (addTask(task)) {
                say(config.FetchComment(CommentTopic.AddTask, CommentContext.OfMemo(taskList, task)));
            }
        } catch (EmptyTaskException e) {
            say(config.FetchComment(CommentTopic.TaskWithoutDescription, CommentContext.OfTask(null, -1)));
        } catch (UndefinedDeadlineException e) {
            say(config.FetchComment(CommentTopic.UndefinedDeadline, CommentContext.OfTask(null, -1)));
        } catch (UndefinedTimeFrameException e) {
            say(config.FetchComment(CommentTopic.UndefinedEventTime, CommentContext.OfTask(null, -1)));
        }
    }

    public void showLogo() {
        System.out.println(config.getLogo() + '\n' + ChatBot.SEPARATOR);
    }

    public void greetUser() {
        System.out.println(config.getGreeting() + ChatBot.SEPARATOR);
    }

    public void sayGoodbye() throws IOException {
        Storage.save(taskList);
        System.out.println(ChatBot.SEPARATOR + '\n' + config.getFarewell());
    }

    public void alert(InputCommand command) {
        say(config.FetchComment(CommentTopic.UndefinedCommand, CommentContext.OfCommand(command)));
    }

    @Override
    public String toString() {
        return config.getName();
    }

    public void deleteTask(int index) {
        Task removed = taskList.removeAt(index);
        if (removed == null) {
            say(config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, index)));
        } else {
            say(config.FetchComment(CommentTopic.RemoveTask, CommentContext.OfTask(removed, index)));
        }
    }
}
