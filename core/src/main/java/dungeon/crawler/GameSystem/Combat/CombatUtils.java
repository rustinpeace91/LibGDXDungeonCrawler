package dungeon.crawler.GameSystem.Combat;

import java.util.HashMap;
import java.util.Map;

import dungeon.crawler.GameSystem.Character.Combatant;

public class CombatUtils {
    
    public static Map<Integer, Combatant> returnCombatElegableCombatants(
        Map<Integer, Combatant> combatantMap
    ) {
        Map<Integer, Combatant> filteredCombatants = new HashMap<>();
        for (Map.Entry<Integer, Combatant> combatant : combatantMap.entrySet()) {
            if (combatant.getValue().canAttack()) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }

    public static Map<Integer, Combatant> returnAliveCombatants(
        Map<Integer, ? extends Combatant> combatantMap
    ) {
        Map<Integer, Combatant> filteredCombatants = new HashMap<>();
        for (Map.Entry<Integer, ? extends Combatant> combatant : combatantMap.entrySet()) {
            if (!combatant.getValue().checkDeath()) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }
}
