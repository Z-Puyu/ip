package common;

import comments.CommentContext;
import comments.CommentTopic;
import inputs.InputCommand;
import reminders.*;

public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final Memo memo = new Memo();

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    public void say(String text) {
        System.out.println(ChatBot.SEPARATOR + '\n' + text.trim() + '\n' + ChatBot.SEPARATOR);
    }

    public void markTask(int index) {
        if (index < 1 || index > this.memo.size()) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = this.memo.get(index - 1);
            task.complete();
            this.say(this.config.FetchComment(CommentTopic.TaskIsDone, CommentContext.OfTask(task, index)));
        }
    }

    public void unmarkTask(int index) {
        if (index < 1 || index > this.memo.size()) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, -1)));
        } else {
            Task task = this.memo.get(index - 1);
            task.reset();
            this.say(this.config.FetchComment(CommentTopic.TaskIsReset, CommentContext.OfTask(task, index)));
        }
    }

    public void denumerateTasks() {
        this.say(this.config.FetchComment(CommentTopic.ListingTask, CommentContext.OfMemo(this.memo, null)));
    }

    public void createTask(InputCommand command) {
        try {
            Task task = Task.from(command);
            if (this.memo.add(task)) {
                this.say(this.config.FetchComment(CommentTopic.AddTask, CommentContext.OfTask(task, this.memo.size())));
            }
        } catch (EmptyTaskException e) {
            this.say(this.config.FetchComment(CommentTopic.TaskWithoutDescription, CommentContext.OfTask(null, -1)));
        } catch (UndefinedDeadlineException e) {
            this.say(this.config.FetchComment(CommentTopic.UndefinedDeadline, CommentContext.OfTask(null, -1)));
        } catch (UndefinedTimeFrameException e) {
            this.say(this.config.FetchComment(CommentTopic.UndefinedEventTime, CommentContext.OfTask(null, -1)));
        }
    }

    public void showLogo() {
        System.out.println(this.config.getLogo() + '\n' + ChatBot.SEPARATOR);
    }

    public void greetUser() {
        System.out.println(this.config.getGreeting() + ChatBot.SEPARATOR);
    }

    public void sayGoodbye() {
        System.out.println(ChatBot.SEPARATOR + '\n' + this.config.getFarewell());
    }

    public void alert(InputCommand command) {
        this.say(this.config.FetchComment(CommentTopic.UndefinedCommand, CommentContext.OfCommand(command)));
    }

    @Override
    public String toString() {
        return this.config.getName();
    }

    public void deleteTask(int index) {
        Task removed = this.memo.removeAt(index);
        if (removed == null) {
            this.say(this.config.FetchComment(CommentTopic.InvalidTask, CommentContext.OfTask(null, index)));
        } else {
            this.say(this.config.FetchComment(CommentTopic.RemoveTask, CommentContext.OfTask(removed, index)));
        }
    }
}
