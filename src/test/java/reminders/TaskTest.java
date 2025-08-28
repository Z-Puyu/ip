package reminders;

import inputs.InputAction;
import inputs.InputCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;

public class TaskTest {

    private static InputCommand makeCommand(InputAction action, String args) {
        return new InputCommand(action, action.toString(), new StringTokenizer(args == null ? "" : args, " "));
    }

    @Test
    void todo_toggleCompletion_uiReflectsChange() {
        Task todo = Task.from(TaskTest.makeCommand(InputAction.CreateTodo, "Buy milk"));
        Assertions.assertNotNull(todo);
        Assertions.assertEquals("[T][ ] Buy milk", todo.toString());
        Assertions.assertFalse(todo.isDone());

        todo.complete();
        Assertions.assertTrue(todo.isDone());
        Assertions.assertEquals("[T][X] Buy milk", todo.toString());

        todo.reset();
        Assertions.assertFalse(todo.isDone());
        Assertions.assertEquals("[T][ ] Buy milk", todo.toString());
    }

    @Test
    void deadline_todayWithoutTime_printsToday() {
        Task dl = Task.from(TaskTest.makeCommand(InputAction.CreateDeadline, "Submit report /by today"));
        Assertions.assertEquals("[D][ ] Submit report (by: today)", dl.toString());
    }

    @Test
    void deadline_todayWithTime_printsTodayAndTime() {
        Task dl = Task.from(TaskTest.makeCommand(InputAction.CreateDeadline, "Submit report /by today 18:30"));
        Assertions.assertEquals("[D][ ] Submit report (by: today 18:30)", dl.toString());
    }

    @Test
    void deadline_specificDateTime_formattedIsoDateTime() {
        Task dl = Task.from(TaskTest.makeCommand(InputAction.CreateDeadline, "Release v1.0 /by Dec 25 2025 09:15"));
        Assertions.assertEquals("[D][ ] Release v1.0 (by: 2025-12-25 09:15)", dl.toString());
    }

    @Test
    void event_todayWithTimes_formattedWithTodayAndTimes() {
        Task ev = Task.from(TaskTest.makeCommand(InputAction.CreateEvent,
                "Team sync /from today 10:00 /to today 11:00"));
        Assertions.assertEquals("[E][ ] Team sync (from: today 10:00 to: today 11:00)", ev.toString());
    }

    @Test
    void event_specificDates_withoutTimes_formattedIsoDates() {
        Task ev = Task.from(TaskTest.makeCommand(InputAction.CreateEvent,
                "Vacation /from Dec 24 2025 /to Dec 26 2025"));
        Assertions.assertEquals("[E][ ] Vacation (from: 2025-12-24 to: 2025-12-26)", ev.toString());
    }

    @Test
    void taskFrom_todoEmptyDescription_throwsEmptyTaskException() {
        Assertions.assertThrows(EmptyTaskException.class, () -> Task.from(TaskTest.makeCommand(InputAction.CreateTodo, "")));
    }

    @Test
    void taskFrom_missingDeadlineAfterBy_throwsUndefinedDeadlineException() {
        Assertions.assertThrows(UndefinedDeadlineException.class,
                () -> Task.from(TaskTest.makeCommand(InputAction.CreateDeadline, "Work hard /by")));
    }

    @Test
    void taskFrom_missingStartOrEndForEvent_throwsUndefinedTimeFrameException() {
        Assertions.assertThrows(UndefinedTimeFrameException.class,
                () -> Task.from(TaskTest.makeCommand(InputAction.CreateEvent, "Meeting /from /to 10:00")));
        Assertions.assertThrows(UndefinedTimeFrameException.class,
                () -> Task.from(TaskTest.makeCommand(InputAction.CreateEvent, "Meeting /from 09:00 /to")));
    }
}
