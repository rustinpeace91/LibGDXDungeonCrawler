package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.Stance;

public class EnemyFactory {

    public static EnemyCombatant generate() {
        // Added 'new' keyword and proper ArrayList initialization
        return new EnemyCombatant(
            "rat",
            3,
            5,
            1,
            16,
            0,
            16,
            0,
            3,
            Stance.STANDING,
            new ArrayList<Condition>(), 
            false
        );
    }
    
}
//     public EnemyCombatant(String name, int toHit, int earnedXP, int initiative, int maxHp, int maxMP, int hp, int mp, int defense, Stance stance, ArrayList<Condition> conditions, boolean isDead) {
