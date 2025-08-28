package reminders;

import inputs.InputCommand;
import common.TimeParser;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

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
     * @param command the input command
     * @return the task
     * @throws EmptyTaskException if the description is empty
     * @throws UndefinedTimeFrameException if the start or end time is empty
     * @throws UndefinedDeadlineException if the deadline is empty
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
        private Temporal date;

        public Deadline(String description, String date) {
            super(description);
            this.date = TimeParser.parse(date);
        }

        @Override
        public String toString() {
            if (this.date instanceof LocalDate d) {
                return String.format("[D][%c] %s (by: %s)", this.isDone() ? 'X' : ' ', this.getDescription(),
                                     d.equals(LocalDate.now()) ? "today" : d.format(DATE_FORMAT));
            } else if (this.date instanceof LocalDateTime dateTime) {
                return String.format("[D][%c] %s (by: %s)", this.isDone() ? 'X' : ' ', this.getDescription(),
                                     dateTime.toLocalDate().equals(LocalDate.now())
                                     ? "today " + dateTime.toLocalTime() : dateTime.format(DATE_TIME_FORMAT));
            }

            return String.format("[D][%c] %s (by: %s)", this.isDone() ? 'X' : ' ', this.getDescription(), this.date);
        }
    }

    private static class Event extends Task {
        private Temporal startTime;
        private Temporal endTime;

        public Event(String description, String startTime, String endTime) {
            super(description);
            this.startTime = TimeParser.parse(startTime);
            this.endTime = TimeParser.parse(endTime);
        }

        @Override
        public String toString() {
            String start = this.startTime.toString();
            if (this.startTime instanceof LocalDateTime s) {
                start = s.toLocalDate().equals(LocalDate.now())
                        ? "today " + s.toLocalTime()
                        : s.format(DATE_TIME_FORMAT);
            } else if (this.startTime instanceof LocalDate d) {
                start = d.equals(LocalDate.now()) ? "today" : d.format(DATE_FORMAT);
            }

            String end = this.endTime.toString();
            if (this.endTime instanceof LocalDateTime e) {
                end = e.toLocalDate().equals(LocalDate.now())
                      ? "today " + e.toLocalTime()
                      : e.format(DATE_TIME_FORMAT);
            } else if (this.endTime instanceof LocalDate e) {
                end = e.equals(LocalDate.now()) ? "today" : e.format(DATE_FORMAT);
            }

            return String.format("[E][%c] %s (from: %s to: %s)", this.isDone() ? 'X' : ' ', this.getDescription(),
                                 start, end);
        }
    }


}
