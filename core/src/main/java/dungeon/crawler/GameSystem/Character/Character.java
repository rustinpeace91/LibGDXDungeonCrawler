package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;

public class Character {
    public int maxHp;
    public int maxMP;
    public int hp;
    public int mp;
    public int defense;
    public int initiative;
    public Stance stance;
    public ArrayList<Condition> conditions;

    public boolean isDead;

    public Character(
        int maxHp,
        int maxMP,
        int hp,
        int mp,
        int defense,
        int initiative,
        Stance stance,
        ArrayList<Condition> conditions,
        boolean isDead
    ) {
        this.maxHp = maxHp;
        this.maxMP = maxMP;
        this.hp = hp;
        this.mp = mp;
        this.defense = defense;
        this.initiative = initiative;
        this.stance = stance;
        this.conditions = conditions;
        this.isDead = isDead;
    }

}
