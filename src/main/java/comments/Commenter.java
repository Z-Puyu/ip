package comments;

public interface Commenter<T> {
    String commentOn(T context);
}
