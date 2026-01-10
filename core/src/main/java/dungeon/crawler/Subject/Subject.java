package dungeon.crawler.Subject;


public interface Subject<T> {
    void addObserver(T observer);
    void removeObserver(T observer);
}
