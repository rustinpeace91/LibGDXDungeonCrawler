package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dungeon.crawler.EnemyParams;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.EnemyCombatant;
import dungeon.crawler.GameSystem.Character.Stance;

public class EnemyFactory {



    public static EnemyCombatant generate() {
        Map<String, EnemyParams> enemyData = new HashMap<>();

        enemyData.put(
            "rat",
            new EnemyParams(
                "rat",
                2,
                50,
                1,
                16,
                0,
                16,
                0
            )
        );
        // Added 'new' keyword and proper ArrayList initialization
        EnemyParams params = enemyData.get("rat");

        // return new EnemyCombatant(
            
        //     Stance.STANDING,s
        //     new ArrayList<Condition>(), 
        //     false
        // );
        return new EnemyCombatant(
            "rat",
            2,
            50,
            1,
            16,
            0,
            6,
            0,
            3,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false
        );
    }

    
    
}
//     public EnemyCombatant(String name, int toHit, int earnedXP, int initiative, int maxHp, int maxMP, int hp, int mp, int defense, Stance stance, ArrayList<Condition> conditions, boolean isDead) {
