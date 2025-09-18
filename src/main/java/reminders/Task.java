package reminders;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Objects;

import common.TimeParser;
import inputs.InputCommand;

/**
 * Represents a task. Contains a description and a boolean indicating whether it is done.
 * A task can be created as a to-do, a deadline or an event.
 * This class is serialisable.
 */
public abstract class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

    private final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
    }

    /**
     * Creates a task from an input command.
     *
     * @param command the input command
     * @return the task
     * @throws EmptyTaskException          if the description is empty
     * @throws UndefinedTimeFrameException if the start or end time is empty
     * @throws UndefinedDeadlineException  if the deadline is empty
     */
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
            if (command.containsToken("/by") || command.containsToken("/from")) {
                throw new EmptyTaskException("No description provided for task.");
            }

            task = new Task.ToDo(desc);
            break;
        case CreateDeadline:
            String date = command.nextArg();
            if (date.isBlank() || !command.containsToken("/by")) {
                throw new UndefinedDeadlineException("No deadline provided for task.");
            }

            task = new Task.Deadline(desc, date);
            break;
        case CreateEvent:
            String start = command.readUntil("/to");
            String end = command.nextArg();
            if (start.isBlank() || end.isBlank() || !command.containsToken("/to") || !command.containsToken("/from")) {
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
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     */
    public void complete() {
        isDone = true;
    }

    /**
     * Resets the task to undone.
     */
    public void reset() {
        isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%c] %s", isDone ? 'X' : ' ', description);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Task t && t.description.equals(description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    private static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return String.format("[T][%c] %s", isDone() ? 'X' : ' ', getDescription());
        }
    }

    private static class Deadline extends Task {
        private final Temporal date;

        public Deadline(String description, String date) {
            super(description);
            this.date = TimeParser.parse(date);
        }

        @Override
        public String toString() {
            if (date instanceof LocalDate d) {
                return String.format("[D][%c] %s (by: %s)", isDone() ? 'X' : ' ', getDescription(),
                        d.equals(LocalDate.now()) ? "today" : d.format(DATE_FORMAT));
            } else if (date instanceof LocalDateTime dateTime) {
                return String.format("[D][%c] %s (by: %s)", isDone() ? 'X' : ' ', getDescription(),
                        dateTime.toLocalDate().equals(LocalDate.now())
                                ? "today " + dateTime.toLocalTime() : dateTime.format(DATE_TIME_FORMAT));
            }

            return String.format("[D][%c] %s (by: %s)", isDone() ? 'X' : ' ', getDescription(), date);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && obj instanceof Deadline ddl && date.equals(ddl.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getDescription(), this.date);
        }
    }

    private static class Event extends Task {
        private final Temporal startTime;
        private final Temporal endTime;

        public Event(String description, String startTime, String endTime) {
            super(description);
            this.startTime = TimeParser.parse(startTime);
            this.endTime = TimeParser.parse(endTime);
        }

        @Override
        public String toString() {
            String start = TimeParser.format(startTime, DATE_TIME_FORMAT, DATE_FORMAT);
            String end = TimeParser.format(endTime, DATE_TIME_FORMAT, DATE_FORMAT);
            return String.format("[E][%c] %s (from: %s to: %s)", isDone() ? 'X' : ' ', getDescription(),
                    start, end);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && obj instanceof Event e
                    && startTime.equals(e.startTime) && endTime.equals(e.endTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getDescription(), this.startTime, this.endTime);
        }
    }
}
