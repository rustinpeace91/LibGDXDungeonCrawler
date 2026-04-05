package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;

public class Enemy extends Character {
    public int earnedXP;
    
    public Enemy(
        String name,
        int earnedXP,
        int initiative,
        int maxHp,
        int maxMP,
        int hp,
        int mp,
        int defense,
        Stance stance,
        ArrayList<Condition> conditions,
        boolean isDead
    ) {
        super(name, maxHp, maxMP, hp, mp, defense, stance, conditions, isDead);
        this.earnedXP = earnedXP;
        this.initiative = initiative;
    }

}
