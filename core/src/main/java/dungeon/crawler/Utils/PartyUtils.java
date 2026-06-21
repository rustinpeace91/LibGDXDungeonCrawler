package dungeon.crawler.Utils;

import java.util.Map;

import dungeon.crawler.GameSystem.Character.PartyCharacter;

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

}
