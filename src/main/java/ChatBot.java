public final class ChatBot {
    private static final int DEFAULT_SEPARATOR_LENGTH = 50;
    private final ChatBotConfig config;

    public ChatBot(ChatBotConfig config) {
        this.config = config;
    }

    private void drawSeparator(int length) {
        System.out.println(new String(new char[length]).replace('\0', '-'));
    }

    public void say(String text) {
        this.drawSeparator(ChatBot.DEFAULT_SEPARATOR_LENGTH);
        System.out.println(text);
        this.drawSeparator(ChatBot.DEFAULT_SEPARATOR_LENGTH);
    }

    public void showLogo() {
        System.out.println(this.config.getLogo());
        this.drawSeparator(ChatBot.DEFAULT_SEPARATOR_LENGTH);
    }

    public void greetUser() {
        System.out.println(this.config.getGreeting());
        this.drawSeparator(ChatBot.DEFAULT_SEPARATOR_LENGTH);
    }

    public void sayGoodbye() {
        this.drawSeparator(ChatBot.DEFAULT_SEPARATOR_LENGTH);
        System.out.println(this.config.getFarewell());
    }

    @Override
    public String toString() {
        return this.config.getName();
    }
}
