package dungeon.crawler.Observers;

import dungeon.crawler.GameSystem.GameState.CombatActionState;

public interface ActionSelectObserver {
    void onActionSelect(CombatActionState state);
}