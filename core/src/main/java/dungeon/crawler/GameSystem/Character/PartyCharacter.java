package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;
import java.util.Random;

import dungeon.crawler.GameSystem.Combat.AttackDamage;
import dungeon.crawler.GameSystem.Inventory.Weapon;
// import dungeon.crawler.GameSystem.TestData.PlayerCharacter;

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
    public CharacterClass charClass;

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
            CharacterClass charClass,
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
        this.fist = new Weapon(
            "fist",
            (PartyCharacter) this,
            this.agility,
            1,
            10,
            "punches",
            false,
            null,
            null,
            (ArrayList<CharacterClass>) null
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
        int toHitRoll = rand.nextInt(20) + 1 + attackWeapon.toHit;
        int damageRoll = rand.nextInt(attackWeapon.damageHigh) + attackWeapon.damageLow;
        String attackText = String.format(
            "%s %s with their %s", name, attackWeapon.flavorTextVerb, attackWeapon.name
        );
        return new AttackDamage(toHitRoll, damageRoll, attackText, false);
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

    public void equipWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
    }

    public ArrayList<String> LevelUp(int newLevel) {
        ArrayList<String> messages = new ArrayList();
        level = newLevel;
        switch(charClass) {
            case HERO:
                Random rand = new Random();
                int newStr = rand.nextInt(3) + 1;
                messages.add(
                    String.format("You are now level %s", String.valueOf(level))
                );
                if(newStr > 0){
                    strength = strength + newStr;
                    messages.add(
                        String.format(
                            "Strength increased by %s",
                            String.valueOf(newStr)
                        )
                    );
                }
                int newaGi = rand.nextInt(4) + 1;
                if(newaGi > 0){
                    agility = agility + newaGi;
                    messages.add(
                        String.format(
                            "Agility increased by %s",
                            String.valueOf(newaGi)
                        )
                    );
                }
                int newInt = rand.nextInt(2);
                    if(newInt > 0){
                        intelligence = intelligence + newInt;
                        messages.add(
                            String.format(
                                "Intelligence increased by %s",
                                String.valueOf(newInt)
                            )
                        );
                    }
                int newPerc = rand.nextInt(2);
                if(newPerc > 0){
                    perception = perception + newPerc;
                    messages.add(
                        String.format(
                            "Perception increased by %s",
                            String.valueOf(newPerc)
                        )
                    );
                }
                break;
            default:
                break;

        }
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
}
