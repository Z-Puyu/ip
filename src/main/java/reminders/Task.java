package reminders;

import inputs.InputCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Task {
    private final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
    }

    public static Task from(InputCommand command) throws EmptyTaskException, UndefinedTimeFrameException,
            UndefinedDeadlineException {
        if (!command.args().hasMoreTokens()) {
            throw new EmptyTaskException("No description provided for task.");
        }

        String desc = command.readUntil("/by", "/from");
        if (desc.isBlank()) {
            throw new EmptyTaskException("No description provided for task.");
        }

        Task task = null;
        switch (command.action()) {
            case CreateTodo:
                task = new Task.ToDo(desc);
                break;
            case CreateDeadline:
                String date = command.nextArg();
                if (date.isBlank()) {
                    throw new UndefinedDeadlineException("No deadline provided for task.");
                }

                task = new Task.Deadline(desc, date);
                break;
            case CreateEvent:
                String start = command.readUntil("/to");
                String end = command.nextArg();
                if (start.isBlank() || end.isBlank()) {
                    throw new UndefinedTimeFrameException("No start or end date provided for task.");
                }

                task = new Task.Event(desc, start, end);
                break;
            default:
                break;
        }

        return task;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void complete() {
        this.isDone = true;
    }

    public void reset() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%c] %s", this.isDone ? 'X' : ' ', this.description);
    }

    private static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return String.format("[T][%c] %s", this.isDone() ? 'X' : ' ', this.getDescription());
        }
    }

    private static class Deadline extends Task {
        private String date;

        public Deadline(String description, String date) {
            super(description);
            this.date = date;
        }

        @Override
        public String toString() {
            return String.format("[D][%c] %s (by: %s)", this.isDone() ? 'X' : ' ', this.getDescription(), this.date);
        }
    }

    private static class Event extends Task {
        private String startTime;
        private String endTime;

        public Event(String description, String startTime, String endTime) {
            super(description);
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return String.format(
                    "[E][%c] %s (from: %s to: %s)", this.isDone() ? 'X' : ' ', this.getDescription(),
                    this.startTime, this.endTime
            );
        }
    }


}
