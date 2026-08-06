package dungeon.crawler.Observers;

import dungeon.crawler.Data.Spells.SpellNames;
import dungeon.crawler.GameSystem.GameState.CombatActionState;

public interface ActionSelectObserver {
    void onActionSelect(int CombatantID, CombatActionState state, int targetid);
    void onActionSelect(int CombatantID, CombatActionState state, int targetid, SpellNames spellName);
    void onActionSelect(int CombatantID, CombatActionState state, int targetid, Item item);
    void onPlayerActionSelectComplete();
    void onActionMenuReset();
}
