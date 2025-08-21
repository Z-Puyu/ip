public final class ChatBot {
    private static final String SEPARATOR = new String(new char[50]).replace('\0', '-');
    private final ChatBotConfig config;
    private final Memo memo = new Memo();

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    private void drawSeparator(int length) {
        System.out.println();
    }

    public void say(String text) {
        System.out.println(ChatBot.SEPARATOR + '\n' + text + '\n' + ChatBot.SEPARATOR);
    }

    public void store(String taskName) {
        Task task = new Task(taskName);
        if (this.memo.add(task)) {
            String comment = String.format(
                    "%s? Oh, good choice. Well, good for me. Now go, before I change my mind.", task.name()
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
