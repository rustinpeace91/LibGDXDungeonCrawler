package dungeon.crawler.Controls;

public interface ControllerAdapter {
    public void attach(GameInputHandler inputHandler);
    public void detach();
}
