package dungeon.crawler.GameSystem.Combat;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Stance;
import dungeon.crawler.GameSystem.Inventory.Item;

public class CombatUtils {

    public static TreeMap<Integer, Combatant> returnCombatElegableCombatants(
        Map<Integer, ? extends Combatant> combatantMap
    ) {
        TreeMap<Integer, Combatant> filteredCombatants = new TreeMap<>();
        for (Map.Entry<Integer, ? extends Combatant> combatant : combatantMap.entrySet()) {
            if (combatant.getValue().canAttack()) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }

    public static ArrayList<Combatant> orderedEligebileCombatants(
        Map<Integer, Combatant> combatantMap
    ){
        TreeMap<Integer, Combatant> eligableCombatants = returnCombatElegableCombatants(combatantMap);
        return new ArrayList<Combatant>(eligableCombatants.values());
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

    public static Map<Integer, Combatant> returnDeadCombatants(
        Map<Integer, ? extends Combatant> combatantMap
    ) {
        Map<Integer, Combatant> filteredCombatants = new HashMap<>();
        for (Map.Entry<Integer, ? extends Combatant> combatant : combatantMap.entrySet()) {
            if (combatant.getValue().checkDeath()) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }

    public static Map<Integer, Combatant> returnItemUseCombatants(
        Map<Integer, ? extends Combatant> combatantMap, Item item
    ) {
        Map<Integer, Combatant> filteredCombatants = new HashMap<>();
        for (Map.Entry<Integer, ? extends Combatant> combatant : combatantMap.entrySet()) {
            if (
                !combatant.getValue().checkDeath() &&
                combatant.getValue().getStance() != Stance.COVER &&
                combatant.getValue().getStance() != Stance.FLYING
            ) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }

}
