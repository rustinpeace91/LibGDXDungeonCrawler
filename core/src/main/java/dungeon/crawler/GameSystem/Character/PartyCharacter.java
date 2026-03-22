package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;

public class PartyCharacter extends Character{
    public int level;
    public int strength;
    public int agility;
    public int intelligence;
    public int perception;
    public boolean isHero;
    public PartyCharacter(int maxHp, int maxMP, int hp, int mp, int defense, int initiative, Stance stance,
            ArrayList<Condition> conditions, boolean isDead, int level, int strength, int agility, int intelligence,
            int perception, boolean isHero) {
        super(maxHp, maxMP, hp, mp, defense, initiative, stance, conditions, isDead);
        this.level = level;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.perception = perception;
        this.isHero = isHero;
    }

}
