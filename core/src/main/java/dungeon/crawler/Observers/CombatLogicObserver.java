package dungeon.crawler.Observers;

public interface CombatLogicObserver {
    public void onActionMenuFocus();
    public void onEventScreenFocus();
    public void onActionSelectComplete();
    public void onActionComplete();
    public void onLoss();
    public void onVictory();

}
