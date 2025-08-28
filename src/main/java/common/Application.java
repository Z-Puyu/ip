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
import inputs.Ui;

import java.io.IOException;

public class Application {
    private static Application instance;

    private boolean isRunning;
    private final ChatBot bot;
    private final Ui input = new Ui();

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
         bot = new ChatBot(config);
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
         input.link("list", InputAction.DenumerateTasks, cmd ->  bot.denumerateTasks(null))
                  .link("find", InputAction.FindTasks,
                        cmd ->  bot.denumerateTasks(task -> task.getDescription().contains(cmd.nextArg())))
                  .link("mark", InputAction.MarkTask, cmd ->  bot.markTask(cmd.nextArg(Integer::parseInt)))
                  .link("unmark", InputAction.UnmarkTask, cmd ->  bot.unmarkTask(cmd.nextArg(Integer::parseInt)))
                  .link("bye", InputAction.Quit, cmd ->  quit())
                  .link("todo", InputAction.CreateTodo,  bot::createTask)
                  .link("deadline", InputAction.CreateDeadline,  bot::createTask)
                  .link("event", InputAction.CreateEvent,  bot::createTask)
                  .link("delete", InputAction.DeleteTask, cmd ->  bot.deleteTask(cmd.nextArg(Integer::parseInt)))
                  .addListener(InputAction.Undefined,  bot::alert);
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
         isRunning = true;
         bot.showLogo();
         bot.greetUser();
        Storage.load( bot);
         setUpInput();
         input.run();
    }

    public void quit() {
        try {
             bot.sayGoodbye();
        } catch (IOException e) {
             bot.say("Failed to save data.");
        }

         isRunning = false;
        System.exit(0);
    }
}
