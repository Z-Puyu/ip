package common;

import task.Memo;
import task.Task;

import java.util.StringTokenizer;

public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final Memo memo = new Memo();

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    public void say(String text) {
        System.out.println(ChatBot.SEPARATOR + '\n' + text + '\n' + ChatBot.SEPARATOR);
    }

    public void markTask(int index) {
        if (index < 1 || index > this.memo.size()) {
            this.say("Now that's the real question, isn't it? Because honestly, that task doesn't exist! Ha!");
        } else {
            Task task = this.memo.get(index - 1);
            task.complete();
            this.say(task.getDescription() + "? Check.\n" + task);
        }
    }

    public void unmarkTask(int index) {
        if (index < 1 || index > this.memo.size()) {
            this.say("Now that's the real question, isn't it? Because honestly, that task doesn't exist! Ha!");
        } else {
            Task task = this.memo.get(index - 1);
            task.reset();
            this.say("Time to return to the hum drum day-to-day.\n" + task);
        }
    }

    public void denumerateTasks() {
        StringBuilder sb = new StringBuilder("Your mortal obligations await!\n");
        for (int i = 0; i < this.memo.size(); i += 1) {
            sb.append(String.format("%d.%s", i + 1, this.memo.get(i)));
            if (i + 1 < this.memo.size()) {
                sb.append("\n");
            }
        }

        this.say(sb.toString());
    }

    public void store(String taskName) {
        Task task = new Task(taskName);
        if (this.memo.add(task)) {
            String comment = String.format(
                    "%s? Oh, good choice. Well, good for me. Now go, before I change my mind.", taskName
            );
            this.say(comment);
        } else {
            this.say("Ha! That task already exists.");
        }
    }

    public void showLogo() {
        System.out.println(this.config.logo() + '\n' + ChatBot.SEPARATOR);
    }

    public void greetUser() {
        System.out.println(this.config.greeting() + '\n' + ChatBot.SEPARATOR);
    }

    public void sayGoodbye() {
        System.out.println(ChatBot.SEPARATOR + '\n' + this.config.farewell());
    }

    @Override
    public String toString() {
        return this.config.name();
    }
}
