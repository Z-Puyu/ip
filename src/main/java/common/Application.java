package common;

import comments.CommentTopic;
import comments.SheogorathAddTaskCommenter;
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
import inputs.InputHandler;
import reminders.Memo;

import java.io.IOException;

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
        return new ChatBotConfig.Builder()
                .withName("Sheogorath")
                .withLogo(logo)
                .withGreeting(greeting)
                .withFarewell(farewell)
                .withCommenter(CommentTopic.RemoveTask, new SheogorathRemoveTaskCommenter())
                .withCommenter(CommentTopic.InvalidTask, new SheogorathInvalidTaskCommenter())
                .withCommenter(CommentTopic.UndefinedCommand, new SheogorathUndefinedCommandCommenter())
                .withCommenter(CommentTopic.AddTask, new SheogorathAddTaskCommenter())
                .withCommenter(CommentTopic.ListingTask, new SheogorathListTasksCommenter())
                .withCommenter(CommentTopic.TaskIsDone, new SheogorathTaskDoneCommenter())
                .withCommenter(CommentTopic.TaskIsReset, new SheogorathTaskResetCommenter())
                .withCommenter(CommentTopic.TaskWithoutDescription, new SheogorathEmptyTaskCommenter())
                .withCommenter(CommentTopic.UndefinedDeadline, new SheogorathUndefinedDeadlineCommenter())
                .withCommenter(CommentTopic.UndefinedEventTime, new SheogorathUndefinedTimeFrameCommenter())
                .build();
    }

    private void setUpInput() {
        this.input.link("list", InputAction.DenumerateTasks, cmd -> this.bot.denumerateTasks())
                  .link("mark", InputAction.MarkTask, cmd -> this.bot.markTask(cmd.nextArg(Integer::parseInt)))
                  .link("unmark", InputAction.UnmarkTask, cmd -> this.bot.unmarkTask(cmd.nextArg(Integer::parseInt)))
                  .link("bye", InputAction.Quit, cmd -> this.quit())
                  .link("todo", InputAction.CreateTodo, this.bot::createTask)
                  .link("deadline", InputAction.CreateDeadline, this.bot::createTask)
                  .link("event", InputAction.CreateEvent, this.bot::createTask)
                  .link("delete", InputAction.DeleteTask, cmd -> this.bot.deleteTask(cmd.nextArg(Integer::parseInt)))
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
        this.bot.recollectTasks();
        this.setUpInput();
        this.input.run();
    }

    public void quit() {
        this.bot.sayGoodbye();
        try {
            this.saveData();
        } catch (IOException e) {
            this.bot.say("Failed to save data.");
        }

        this.isRunning = false;
        System.exit(0);
    }

    private void saveData() throws IOException {
        this.bot.rememberTasks();
    }
}
