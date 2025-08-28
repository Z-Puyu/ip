package reminders;

import inputs.InputAction;
import inputs.InputCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class TaskListTest {

    private static InputCommand makeCommand(InputAction action, String args) {
        return new InputCommand(action, action.toString(), new StringTokenizer(args == null ? "" : args, " "));
    }

    private static Task makeTodo(String desc) {
        return Task.from(makeCommand(InputAction.CreateTodo, desc));
    }

    @Test
    void sizeAddGet_addTwoTasks_sizeAndGetReflectsInsertion() {
        TaskList list = new TaskList();
        Assertions.assertEquals(0, list.size());

        Task t1 = makeTodo("a");
        Task t2 = makeTodo("b");
        Assertions.assertTrue(list.add(t1));
        Assertions.assertTrue(list.add(t2));
        Assertions.assertEquals(2, list.size());

        Assertions.assertSame(t1, list.get(0));
        Assertions.assertSame(t2, list.get(1));
    }

    @Test
    void removeAt_middleTask_removedAndReturnedCorrectly() {
        TaskList list = new TaskList();
        Task t1 = makeTodo("first");
        Task t2 = makeTodo("second");
        Task t3 = makeTodo("third");
        list.add(t1);
        list.add(t2);
        list.add(t3);

        Task removed = list.removeAt(1); // remove middle
        Assertions.assertSame(t2, removed);
        Assertions.assertEquals(2, list.size());
        Assertions.assertSame(t1, list.get(0));
        Assertions.assertSame(t3, list.get(1));
    }

    @Test
    void iterator_traversal_insertionOrder_preserved() {
        TaskList list = new TaskList();
        Task t1 = makeTodo("1");
        Task t2 = makeTodo("2");
        Task t3 = makeTodo("3");
        list.add(t1);
        list.add(t2);
        list.add(t3);

        List<Task> seen = new ArrayList<>();
        for (Task task : list) {
            seen.add(task);
        }

        Assertions.assertArrayEquals(new Task[]{t1, t2, t3}, seen.toArray(new Task[0]));
    }

    @Test
    void forEach_applyIncrementAction_allTasksVisited() {
        TaskList list = new TaskList();
        Task t1 = makeTodo("alpha");
        Task t2 = makeTodo("beta");
        list.add(t1);
        list.add(t2);

        int[] count = {0};
        list.forEach(t -> count[0] += 1);
        Assertions.assertEquals(2, count[0]);
    }
}
