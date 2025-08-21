public class Application {
    private static Application instance;

    private String logo;
    private ChatBot bot;

    private Application(String logo, ChatBot bot) {
        this.logo = logo;
        this.bot = bot;
    }

    public static Application instance() {
        if (instance == null) {
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
            instance = new Application(logo, new ChatBot("Sheogorath"));
        }

        return Application.instance;
    }

    public void Boot() {
        this.bot.GreetUser();
        System.out.println(logo);
    }

    public void Quit() {
        this.bot.SayGoodbye();
    }
}
