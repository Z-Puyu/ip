package inputs;

/**
 * Represents an action that can be performed by the user.
 */
public enum InputAction {
    Quit,
    Undefined,
    DeleteTask {
        @Override
        public String toString() {
            return "Delete Task";
        }
    },
    CreateTodo {
        @Override
        public String toString() {
            return "Create Todo";
        }
    },
    CreateDeadline {
        @Override
        public String toString() {
            return "Create Task with Deadline";
        }
    },
    CreateEvent {
        @Override
        public String toString() {
            return "Create Event";
        }
    },
    DenumerateTasks {
        @Override
        public String toString() {
            return "Denumerate Tasks";
        }
    },
    MarkTask {
        @Override
        public String toString() {
            return "Mark Task";
        }
    },
    UnmarkTask {
        @Override
        public String toString() {
            return "Unmark Task";
        }
    },
    FindTasks {
        @Override
        public String toString() {
            return "Find Tasks";
        }
    }
}
