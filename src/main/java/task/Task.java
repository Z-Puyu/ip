package task;

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
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
}
