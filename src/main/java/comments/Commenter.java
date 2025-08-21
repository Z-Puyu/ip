package comments;

public interface Commenter<T> {
    public String commentOn(T context);
}
