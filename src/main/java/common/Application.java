package common;

import comments.*;
import inputs.InputAction;
import inputs.InputHandler;

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
        ChatBotConfig config = Application.getConfig(logo);
        this.bot = new ChatBot(config);
    }

    private static ChatBotConfig getConfig(String logo) {
        String greeting = """
                Hey, you! Finally awake!
                You know me. You just don't know it.
                Sheogorath, Daedric Prince of Madness. At your service.
                """;
        String farewell = "Well, I suppose it's back to the Shivering Isles.\n" +
                "And as for you, my little mortal minion... Feel free to keep the Wabbajack.";
        String noTaskDescComment = "Task description! Now where did you leave my task description?";
        String noEventTimeComment = """
                When does this begin? And when does it end?
                Well? Spit it out, mortal. I haven't got an eternity! 
                Actually... I do. Little joke.
                """;
        String noDeadlineComment = """
                By when you want it done?
                Well? Spit it out, mortal. I haven't got an eternity! 
                Actually... I do. Little joke.
                """;
        return new ChatBotConfig(
                "Sheogorath", logo, greeting, farewell,
                new SheogorathInvalidTaskCommenter(), new SheogorathAddTaskCommenter(),
                new SheogorathListTasksCommenter(), new SheogorathTaskDoneCommenter(),
                new SheogorathTaskResetCommenter(), new SheogorathUndefinedCommandCommenter(),
                noTaskDescComment, noDeadlineComment, noEventTimeComment
        );
    }

    private void setUpInput() {
        this.input.link("list", InputAction.DenumerateTasks, cmd -> this.bot.denumerateTasks())
                  .link("mark", InputAction.MarkTask, cmd -> this.bot.markTask(cmd.nextArg(Integer::parseInt)))
                  .link("unmark", InputAction.UnmarkTask, cmd -> this.bot.unmarkTask(cmd.nextArg(Integer::parseInt)))
                  .link("bye", InputAction.Quit, cmd -> this.quit())
                  .link("todo", InputAction.CreateTodo, this.bot::createTask)
                  .link("deadline", InputAction.CreateDeadline, this.bot::createTask)
                  .link("event", InputAction.CreateEvent, this.bot::createTask)
                  .addListener(InputAction.Undefined, this.bot::alert);
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
        this.bot.showLogo();
        this.bot.greetUser();
        this.setUpInput();
        this.input.run();
    }

    public void quit() {
        this.bot.sayGoodbye();
        this.isRunning = false;
        System.exit(0);
    }
}
