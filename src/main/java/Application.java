public class Application {
    private static Application instance;

    private boolean isRunning;
    private final ChatBot bot;
    private final InputHandler input = new InputHandler();

    private Application() {
        String logo = """
                                                                       .---.                _..._                  \s
                                                                       |   |             .-'_..._''.               \s
                                     /|        /|                      '---'           .' .'      '.\\    .         \s
                       _     _       ||        ||                      .---.          / .'             .'|         \s
                 /\\    \\\\   //       ||        ||                      |   |         . '             .'  |         \s
                 `\\\\  //\\\\ //  __    ||  __    ||  __        __        |   |    __   | |            <    |         \s
                   \\`//  \\'/.:--.'.  ||/'__ '. ||/'__ '.  .:--.'.      |   | .:--.'. | |             |   | ____    \s
                    \\|   |// |   \\ | |:/`  '. '|:/`  '. '/ |   \\ |     |   |/ |   \\ |. '             |   | \\ .'    \s
                     '     `" __ | | ||     | |||     | |`" __ | |     |   |`" __ | | \\ '.          .|   |/  .     \s
                            .'.''| | ||\\    / '||\\    / ' .'.''| |     |   | .'.''| |  '. `._____.-'/|    /\\  \\    \s
                           / /   | |_|/\\'..' / |/\\'..' / / /   | |_ __.'   '/ /   | |_   `-.______ / |   |  \\  \\   \s
                           \\ \\._,\\ '/'  `'-'`  '  `'-'`  \\ \\._,\\ '/|      ' \\ \\._,\\ '/            `  '    \\  \\  \\  \s
                            `--'  `"                      `--'  `" |____.'   `--'  `"               '------'  '---'\s
                """;
        String greeting = """
                Hey, you! Finally awake!
                You know me. You just don't know it.
                Sheogorath, Daedric Prince of Madness. At your service.
                """;
        String farewell = "Well, I suppose it's back to the Shivering Isles.";
        ChatBotConfig config = new ChatBotConfig("Sheogorath", logo, greeting, farewell);
        this.bot = new ChatBot(config);
    }

    private void setUpInput() {
        InputMapping.getInstance()
                    .map("bye", InputAction.Quit);
        this.input.addListener(InputAction.EnterText, this.bot::store)
                  .addListener(InputAction.Quit, args -> this.quit());
    }

    public static Application fetchInstance() {
        if (Application.instance == null) {
            Application.instance = new Application();
        }

        return Application.instance;
    }

    public static boolean isRunning() {
        return Application.instance != null && Application.instance.isRunning;
    }

    public void boot() {
        this.isRunning = true;
        this.bot.greetUser();
        this.bot.showLogo();
        this.setUpInput();
        this.input.run();
    }

    public void quit() {
        this.bot.sayGoodbye();
        this.isRunning = false;
        System.exit(0);
    }
}
