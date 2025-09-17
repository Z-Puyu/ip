package common;

import java.io.IOException;

import comments.CommentTopic;
import comments.SheogorathAddTaskCommenter;
import comments.SheogorathDuplicateTaskCommenter;
import comments.SheogorathEmptyTaskCommenter;
import comments.SheogorathInvalidTaskCommenter;
import comments.SheogorathListTasksCommenter;
import comments.SheogorathRemoveTaskCommenter;
import comments.SheogorathTaskDoneCommenter;
import comments.SheogorathTaskResetCommenter;
import comments.SheogorathUndefinedCommandCommenter;
import comments.SheogorathUndefinedDeadlineCommenter;
import comments.SheogorathUndefinedTimeFrameCommenter;
import inputs.InputAction;
import inputs.Ui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * The main application class.
 */
public class App extends Application {
    private static final String LOGO = """
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
    private static App instance;

    private boolean isRunning;
    private final ChatBot bot;
    private final Ui input = new Ui();

    /**
     * Creates the application.
     */
    public App() {
        ChatBotConfig config = App.getConfig();
        bot = new ChatBot(config, input::present);
    }

    private static ChatBotConfig getConfig() {
        String greeting = """
                Hey, you! Finally awake!
                You know me. You just don't know it.
                Sheogorath, Daedric Prince of Madness. At your service.
                """;
        String farewell = "Well, I suppose it's back to the Shivering Isles.\n"
                + "And as for you, my little mortal minion... Feel free to keep the Wabbajack.";
        return new ChatBotConfig.Builder()
                .withName("Sheogorath")
                .withLogo(App.LOGO)
                .withGreeting(greeting)
                .withFarewell(farewell)
                .withCommenter(CommentTopic.RemoveTask, new SheogorathRemoveTaskCommenter())
                .withCommenter(CommentTopic.InvalidTask, new SheogorathInvalidTaskCommenter())
                .withCommenter(CommentTopic.UndefinedCommand, new SheogorathUndefinedCommandCommenter())
                .withCommenter(CommentTopic.AddTask, new SheogorathAddTaskCommenter())
                .withCommenter(CommentTopic.DuplicateTask, new SheogorathDuplicateTaskCommenter())
                .withCommenter(CommentTopic.ListingTask, new SheogorathListTasksCommenter())
                .withCommenter(CommentTopic.TaskIsDone, new SheogorathTaskDoneCommenter())
                .withCommenter(CommentTopic.TaskIsReset, new SheogorathTaskResetCommenter())
                .withCommenter(CommentTopic.TaskWithoutDescription, new SheogorathEmptyTaskCommenter())
                .withCommenter(CommentTopic.UndefinedDeadline, new SheogorathUndefinedDeadlineCommenter())
                .withCommenter(CommentTopic.UndefinedEventTime, new SheogorathUndefinedTimeFrameCommenter())
                .build();
    }

    private void setUpInput() {
        input.link("list", InputAction.DenumerateTasks, cmd -> bot.denumerateTasks(null))
             .link("find", InputAction.FindTasks,
                     cmd -> bot.denumerateTasks(task -> task.getDescription().contains(cmd.nextArg())))
             .link("mark", InputAction.MarkTask, cmd -> bot.markTask(cmd.nextArg(Integer::parseInt)))
             .link("unmark", InputAction.UnmarkTask, cmd -> bot.unmarkTask(cmd.nextArg(Integer::parseInt)))
             .link("bye", InputAction.Quit, cmd -> quit())
             .link("todo", InputAction.CreateTodo, bot::createTask)
             .link("deadline", InputAction.CreateDeadline, bot::createTask)
             .link("event", InputAction.CreateEvent, bot::createTask)
             .link("delete", InputAction.DeleteTask, cmd -> bot.deleteTask(cmd.nextArg(Integer::parseInt)))
             .addListener(InputAction.Undefined, bot::alert);
    }

    /**
     * Fetch the active instance of the application.
     * @return the active instance of the application
     */
    public static App fetchInstance() {
        if (App.instance == null) {
            App.instance = new App();
        }

        return App.instance;
    }

    /**
     * Check if the application is running.
     * @return true if the application is running, false otherwise
     */
    public static boolean isRunning() {
        return App.instance != null && App.instance.isRunning;
    }

    /**
     * Boots the application.
     */
    @Override
    public void start(Stage primaryStage) {
        isRunning = true;
        input.start(primaryStage);
        setUpInput();
        bot.showLogo();
        bot.greetUser();
        Storage.load(bot);
        input.run();
    }

    /**
     * Quits the application.
     */
    public void quit() {
        try {
            bot.sayGoodbye();
        } catch (IOException e) {
            bot.say("Failed to save data.", true);
            System.exit(1);
        }

        isRunning = false;
        Platform.runLater(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(0);
        });
    }
}
