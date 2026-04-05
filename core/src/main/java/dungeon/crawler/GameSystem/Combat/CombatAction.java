package dungeon.crawler.GameSystem.Combat;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.GameState.CombatActionState;

public class CombatAction {
    public Combatant combatant;
    public CombatActionState action;
    public int combatantID;
    public int iniative;
    public Combatant target;
    public CombatAction(
        int id,
        int initiative,
        Combatant combatant,
        CombatActionState action
    ){
        this.combatantID = id;
        this.iniative = initiative;
        this.combatant = combatant;
        this.action = action;
    }
    public CombatAction(
        int id,
        int initiative,
        Combatant combatant,
        CombatActionState action,
        Combatant target
    ) {
        this.combatant = combatant;
        this.action = action;
        this.target = target;
        this.iniative = initiative;
    }
}
