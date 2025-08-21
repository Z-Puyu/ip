public class ChatBotConfig {
    private final String name;
    private final String logo;
    private final String greeting;
    private final String farewell;

    public ChatBotConfig(String name, String logo, String greeting, String farewell) {
        this.name = name;
        this.logo = logo;
        this.greeting = greeting;
        this.farewell = farewell;
    }

    public String getName() {
        return this.name;
    }

    public String getLogo() {
        return this.logo;
    }

    public String getGreeting() {
        return this.greeting;
    }

    public String getFarewell() {
        return this.farewell;
    }
}
