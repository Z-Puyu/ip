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

    public static Task from(InputCommand command) throws IllegalArgumentException {
        if (!command.args().hasMoreTokens()) {
            return new ToDo(command.text());
        }

        String desc = command.readUntil("/by", "/from");
        switch (command.action()) {
            case CreateTodo:
                return new Task.ToDo(desc);
            case CreateDeadline:
                return new Task.Deadline(desc, command.nextArg());
            case CreateEvent:
                String start = command.readUntil("/to");
                String end = command.nextArg();
                return new Task.Event(desc, start, end);
        }

        throw new IllegalArgumentException(String.format("Invalid command: %s", command.text()));
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
