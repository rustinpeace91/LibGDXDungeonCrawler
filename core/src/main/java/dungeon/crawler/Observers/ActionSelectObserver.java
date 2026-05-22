package dungeon.crawler.Observers;

import dungeon.crawler.GameSystem.GameState.CombatActionState;

public interface ActionSelectObserver {
    void onActionSelect(int CombatantID, CombatActionState state, int targetid);
    void onPlayerActionSelectComplete();
    void onActionMenuReset();
}