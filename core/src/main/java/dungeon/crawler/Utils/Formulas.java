package dungeon.crawler.Utils;

import com.badlogic.gdx.math.MathUtils;
import dungeon.crawler.Data.Items.Loot;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.Inventory.Item;

import java.util.ArrayList;
import java.util.Map;
// TODO: Move all formulas here

public class Formulas {

    public static boolean partyCanSteal(Map<Integer, PartyCharacter> party) {
        for (Map.Entry<Integer, PartyCharacter> partyMember : PartyUtils.returnPartyMembers(party).entrySet()) {
            if (partyMember.getValue().charClass.canSteal()) {
                return true;
            }
        }
        return false;
    }

    public static boolean stealSuccessful(Map<Integer, PartyCharacter> party, int shopIndex) {
        int totalAgility = 0;
        int thievesCount = 0;
        shopIndex++;


        for (Map.Entry<Integer, PartyCharacter> entry : party.entrySet()) {
            PartyCharacter member = entry.getValue();
            if (member.charClass.canSteal()) {
                totalAgility += member.agility;
                thievesCount++;
            }
        }
        if (thievesCount == 0) return false;
        int teamworkModifier = (thievesCount - 1) * 15;
        int successPercent = 30 + totalAgility + teamworkModifier - (shopIndex * 20);
        successPercent = MathUtils.clamp(successPercent, 5, 85);
        return MathUtils.random(1, 100) <= successPercent;
    }

    public static Loot stealTreasure(
        Map<Integer, PartyCharacter> party,
        ArrayList<Item> inventory,
        int shopIndex
    ){
        // maybe we'll use agility later if we want to tweak this
        int totalAgility = 0;
        int thievesCount = 0;
        ArrayList<Item> returnItems = new ArrayList<>();
        for (Map.Entry<Integer, PartyCharacter> entry : party.entrySet()) {
            PartyCharacter member = entry.getValue();
            if (member.charClass.canSteal()) {
                totalAgility += member.agility;
                thievesCount++;
            }
            for(int i=0; i<thievesCount; i++){
                if (inventory != null && !inventory.isEmpty()) {
                    int randomIndex = MathUtils.random(0, inventory.size() - 1);
                    Item stolenItem = inventory.remove(randomIndex);
                    returnItems.add(stolenItem);
                }
            }
        }
        int maxGold = 20 * thievesCount * (shopIndex + 1);
        int gold = MathUtils.random(0, maxGold);
        return new Loot(
            gold,
            returnItems
        );

    }

}
