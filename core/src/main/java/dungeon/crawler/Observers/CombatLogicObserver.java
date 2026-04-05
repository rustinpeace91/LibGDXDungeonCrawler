package dungeon.crawler.Observers;

public interface CombatLogicObserver {
    public void onActionMenuFocus();
    public void onActionSelectComplete();
    public void onLoss();
    public void onVictory();

}
