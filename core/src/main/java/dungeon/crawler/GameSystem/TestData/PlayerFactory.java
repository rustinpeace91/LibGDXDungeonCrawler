package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;

public class PlayerFactory {

    public static PartyCharacter generate() {
        return new PartyCharacter(
            "Hero",
            30,
            10,
            30,
            10,
            0,
            10,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            10,
            10,
            10,
            10,
            true
        );
    }
}