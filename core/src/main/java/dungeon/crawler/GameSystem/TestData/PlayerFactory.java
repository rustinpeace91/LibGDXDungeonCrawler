package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.Stance;

public class PlayerFactory {

    public static PlayerCharacter generate() {
        return new PlayerCharacter(
            "Hero",
            30,
            10,
            30,
            10,
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