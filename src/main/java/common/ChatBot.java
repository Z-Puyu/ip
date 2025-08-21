package common;

import inputs.InputCommand;
import reminders.Memo;
import reminders.MemoStateInfo;
import reminders.Task;
import reminders.TaskInfo;

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
        Task task = Task.from(command);
        if (this.memo.add(task)) {
            this.say(this.config.addTaskCommenter().commentOn(new MemoStateInfo(task, this.memo)));
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

    }

    @Override
    public String toString() {
        return this.config.name();
    }
}
