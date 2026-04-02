package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;
import java.util.Random;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.Enemy;
import dungeon.crawler.GameSystem.Character.Stance;
import dungeon.crawler.GameSystem.Combat.AttackDamage;

public class EnemyCombatant extends Enemy implements Combatant {
    public int toHit;

    public EnemyCombatant(
        String name,
        int toHit,
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
        super(name, earnedXP, initiative, maxHp, maxMP, hp, mp, defense, stance, conditions, isDead);
        this.toHit = toHit;
    }

    @Override
    public AttackDamage attack() {
        // TODO Auto-generated method stub
        Random rand = new Random();
        // nextInt(20) gives 0-19, so +1 gives 1-20
        int randomNumber = rand.nextInt(20) + 1;
        int toHitRoll = randomNumber + toHit;
        int damageRoll = rand.nextInt(10) + 1;
        String flavorText = String.format("The %s takes a bite!", this.name);
        return new AttackDamage(toHitRoll, damageRoll,  flavorText, false);
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
        return (rand.nextInt(20) + 1) + initiative;
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
