package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;

public class Enemy extends Character {
    public int earnedXP;
    protected int defense;
    protected String identifier;
    
    public Enemy(
        String name,
        String identifier,
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
        super(name, maxHp, maxMP, hp, mp, stance, conditions, isDead);
        this.earnedXP = earnedXP;
        this.initiative = initiative;
        this.defense = defense;
    }

}
