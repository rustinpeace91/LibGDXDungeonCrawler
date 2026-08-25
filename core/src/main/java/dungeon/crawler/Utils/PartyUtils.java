package dungeon.crawler.Utils;

import java.util.HashMap;
import java.util.Map;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Combat.AttackDamage;

public class PartyUtils {

    public static PartyCharacter returnPartyMemberByName(
        Map<Integer, PartyCharacter> party, String name
    ) {
        for (Map.Entry<Integer, PartyCharacter> partyMember : party.entrySet()) {
            String s = "yeah";
            if (partyMember.getValue().name.equals(name)) {
                return partyMember.getValue();
            }
        }
        return null;
    }

    public static void resurrectDeadPartyMembers(Map<Integer, PartyCharacter> party){
        for (Map.Entry<Integer, PartyCharacter> partyMember : party.entrySet()) {
            if(partyMember.getValue().isDead){
                partyMember.getValue().resurrect();
            }
        }
    }

    public static void cureAllAilments(Map<Integer, PartyCharacter> party){
        for (Map.Entry<Integer, PartyCharacter> partyMember : party.entrySet()) {
            partyMember.getValue().removeAllStatuses();
        }
    }

    public static Map<Integer, PartyCharacter> returnPartyMembers(
        Map<Integer, PartyCharacter> combatantMap
    ) {
        Map<Integer, PartyCharacter> filteredCombatants = new HashMap<>();
        for (Map.Entry<Integer,PartyCharacter> combatant : combatantMap.entrySet()) {
            if (!combatant.getValue().checkDeath()) {
                filteredCombatants.put(combatant.getKey(), combatant.getValue());
            }
        }
        return filteredCombatants;
    }

    public static void killAllThieves(Map<Integer, PartyCharacter> party){
        for (Map.Entry<Integer, PartyCharacter> partyMember : party.entrySet()) {
            if(partyMember.getValue().charClass.canSteal()){
                AttackDamage dmg = new AttackDamage(
                    999,
                    partyMember.getValue().maxHp + 1,
                    "you dead",
                    false
                );
                partyMember.getValue().takeHit(dmg);
                partyMember.getValue().checkDeath();
            }
            partyMember.getValue().removeAllStatuses();
        }
    }
}
