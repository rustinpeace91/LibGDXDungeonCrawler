package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;
import java.util.Random;

import dungeon.crawler.GameSystem.Combat.AttackDamage;

public class EnemyCombatant extends Enemy implements Combatant {
    public int toHit;
    private int attackMin;
    private int attackMax;
    private String attackText;

    public EnemyCombatant(
        String name,
        String identifier,
        int toHit,
        int earnedXP,
        int initiative,
        int maxHp,
        int maxMP,
        int hp,
        int mp,
        int defense,
        int attackMin,
        int attackMax,
        String attackText,
        Stance stance,
        ArrayList<Condition> conditions,
        boolean isDead
    ) {
        super(
            name,
            identifier,
            earnedXP,
            initiative,
            maxHp,
            maxMP,
            hp,
            mp,
            defense,
            stance,
            conditions,
            isDead
        );
        this.toHit = toHit;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.attackText = attackText;
    }

    @Override
    public AttackDamage attack() {
        // TODO Auto-generated method stub
        Random rand = new Random();
        // nextInt(20) gives 0-19, so +1 gives 1-20
        int randomNumber = rand.nextInt(20) + 1;
        int toHitRoll = randomNumber + toHit;
        int damageRoll = rand.nextInt(attackMax) + attackMin;
        String flavorText = attackText;
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

    @Override
    public int getDefense() {
        // TODO Auto-generated method stub
        return defense;
    }

    @Override
    public int heal(int amount){
        int boost;
        if(hp + amount > maxHp){
            boost = maxHp - hp;
            hp = maxHp;
        } else{
            hp = hp + amount;
            boost = amount;
        }
        return boost;
    }

    @Override
    public void spendMp(int amount) {
        // TODO Auto-generated method stub

    }

}
