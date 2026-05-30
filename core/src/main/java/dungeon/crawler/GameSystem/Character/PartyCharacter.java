package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.*;
import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Combat.AttackDamage;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.Utils.StringUtils;

public class PartyCharacter extends Character implements Combatant{
    public int level;
    public int xp;
    public int strength;
    public int agility;
    public int intelligence;
    public int perception;
    public boolean isHero;
    public int toHit;
    public Weapon equippedWeapon;
    public Weapon fist;
    public ClassLogic charClass;

    public PartyCharacter(
        String name,
        int maxHp,
        int maxMP,
        int hp,
        int mp,
        int xp,
        Stance stance,
        ArrayList<Condition> conditions,
        boolean isDead,
        int level,
        int strength,
        int agility,
        int intelligence,
        int perception,
        ClassLogic charClass,
        boolean isHero

    ) {
        super(name, maxHp, maxMP, hp, mp, stance, conditions, isDead);
        this.level = level;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.perception = perception;
        this.isHero = isHero;
        this.xp = xp;
        this.toHit = calculateToHit();
        this.fist = new Weapon(
            "fist",
            (PartyCharacter) this,
            this.agility,
            1,
            10,
            "punches",
            false,
            null,
            null
        );
        this.charClass = charClass;
    }

    public int calculateToHit(){
        return 1;
    }

    @Override
    public AttackDamage attack() {
        // TODO Auto-generated method stub

        Weapon attackWeapon = getWeapon();
        Random rand = new Random();
        int agilityModofier = Math.round(agility / 10);
        int toHitRoll = rand.nextInt(20) + 1 + attackWeapon.toHit + getToHit();
        int damageRoll = rand.nextInt(attackWeapon.damageHigh) + attackWeapon.damageLow + getDamageBonus();
        String attackText = StringUtils.format(
            "%s %s with their %s", name, attackWeapon.flavorTextVerb, attackWeapon.name
        );
        return new AttackDamage(toHitRoll, damageRoll, attackText, false);
    }

    public int getToHit(){
        return  Math.round(agility / 10) + getWeapon().toHit;
    }

    public int getDamageBonus(){
        return Math.round(strength/10);
    }
    @Override
    public int defend(AttackDamage attack) {
        // TODO Auto-generated method stub
        return getDefense();
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

    public void equipWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
    }

    public ArrayList<String> LevelUp(int newLevel) {
        ArrayList<String> messages = new ArrayList();
        level = newLevel;

        Map<GameConstants.PLAYER_STATS, Integer> levelUpStats = charClass.returnLevelUpStats();
        levelUpStats.entrySet().stream()
            .filter(entry -> entry.getValue() > 0)
            .forEach(
                entry -> {
                    messages.add(
                        StringUtils.format(
                            "%s increased by %s", GameConstants.STAT_NAMES.get(entry.getKey()), entry.getValue()
                        )
                    );
                }
            );
        this.strength = levelUpStats.get(STRENGTH);
        this.intelligence = levelUpStats.get(INTELLIGENCE);
        this.agility = levelUpStats.get(AGILITY);
        this.perception = levelUpStats.get(PERCEPTION);

        return messages;
    }

    public Weapon getWeapon(){
        if(
            equippedWeapon != null
        ){
            return equippedWeapon;
        } else {
            return fist;
        }
    }

    @Override
    public int getDefense() {
        // TODO Auto-generated method stub
        return 10;
    }
}
