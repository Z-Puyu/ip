package common;

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
            this.say(this.config.invalidTaskCommenter().commentOn(new TaskInfo(null, -1)));
        } else {
            Task task = this.memo.get(index - 1);
            task.complete();
            this.say(this.config.markTaskCommenter().commentOn(new TaskInfo(task, index)));
        }
    }

    public void unmarkTask(int index) {
        if (index < 1 || index > this.memo.size()) {
            this.say(this.config.invalidTaskCommenter().commentOn(new TaskInfo(null, -1)));
        } else {
            Task task = this.memo.get(index - 1);
            task.reset();
            this.say(this.config.unmarkTaskCommenter().commentOn(new TaskInfo(task, index)));
        }
    }

    public void denumerateTasks() {
        this.say(this.config.listTasksCommenter().commentOn(new MemoStateInfo(null, this.memo)));
    }

    public void createTask(InputCommand command) {
        try {
            Task task = Task.from(command);
            if (this.memo.add(task)) {
                this.say(this.config.addTaskCommenter().commentOn(new MemoStateInfo(task, this.memo)));
            }
        } catch (EmptyTaskException e) {
            this.say(this.config.missingTaskDescriptionComment());
        } catch (UndefinedDeadlineException e) {
            this.say(this.config.missingDeadlineComment());
        } catch (UndefinedTimeFrameException e) {
            this.say(this.config.missingEventTimeComment());
        }
    }

    public void showLogo() {
        System.out.println(this.config.logo() + '\n' + ChatBot.SEPARATOR);
    }

    public void greetUser() {
        System.out.println(this.config.greeting() + ChatBot.SEPARATOR);
    }

    public void sayGoodbye() {
        System.out.println(ChatBot.SEPARATOR + '\n' + this.config.farewell());
    }

    public void alert(InputCommand command) {
        this.say(this.config.undefinedCommandCommenter().commentOn(command));
    }

    @Override
    public String toString() {
        return this.config.name();
    }

    public void deleteTask(int index) {
        Task removed = this.memo.removeAt(index);
        if (removed == null) {
            this.say(this.config.invalidTaskCommenter().commentOn(new TaskInfo(null, index)));
        } else {
            this.say(this.config.removeTaskCommenter().commentOn(new TaskInfo(removed, index)));
        }
    }
}
