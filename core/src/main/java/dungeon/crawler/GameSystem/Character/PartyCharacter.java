package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;
import java.util.Random;

import dungeon.crawler.GameSystem.Combat.AttackDamage;

public class PartyCharacter extends Character implements Combatant{
    public int level;
    public int xp;
    public int strength;
    public int agility;
    public int intelligence;
    public int perception;
    public boolean isHero;
    public int toHit;
    public PartyCharacter(
            String name,
            int maxHp,
            int maxMP,
            int hp,
            int mp,
            int xp,
            int defense,
            Stance stance,
            ArrayList<Condition> conditions,
            boolean isDead,
            int level,
            int strength,
            int agility,
            int intelligence,
            int perception,
            boolean isHero

    ) {
        super(name, maxHp, maxMP, hp, mp, defense, stance, conditions, isDead);
        this.level = level;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.perception = perception;
        this.isHero = isHero;
        this.xp = xp;
        this.toHit = calculateToHit();
    }

    public int calculateToHit(){
        return 1;
    }

    @Override
    public AttackDamage attack() {
        // TODO Auto-generated method stub
        Random rand = new Random();
        // nextInt(20) gives 0-19, so +1 gives 1-20
        int randomNumber = rand.nextInt(20) + 1;
        int toHit = randomNumber + 1;
        int damageRoll = rand.nextInt(10) + 1;
        return new AttackDamage(toHit, damageRoll, "You swing your sword", false);
    }

    @Override
    public int defend(AttackDamage attack) {
        // TODO Auto-generated method stub
        return defense;

        
    }

    @Override
    public int takeHit(AttackDamage attack) {
        // TODO Auto-generated method stub
        hp = hp - attack.damage;
        return attack.damage;

    }

    @Override
    public int rollInitiative(){
        Random rand = new Random();
        int agilityMod = (int) Math.ceil(this.agility / 10.0);
        return (rand.nextInt(20) + 1) + agilityMod;
    }

    @Override
    public boolean checkDeath(){
        isDead = hp <= 0;
        return isDead;
    }

    @Override
    public boolean canAttack(){
        return !isDead;
    }

    @Override
    public String getName(){
        return name;
    }
}
