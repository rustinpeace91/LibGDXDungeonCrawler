package dungeon.crawler.GameSystem.Character;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.AGILITY;
import static dungeon.crawler.GameConstants.PLAYER_STATS.INTELLIGENCE;
import static dungeon.crawler.GameConstants.PLAYER_STATS.PERCEPTION;
import static dungeon.crawler.GameConstants.PLAYER_STATS.STRENGTH;
import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Combat.AttackDamage;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.GameSystem.Inventory.EquipmentSystem.EquipmentSystem;
import dungeon.crawler.GameSystem.Inventory.InventorySystem.InventorySystem;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.Handed;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;
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
    // public Weapon equippedWeapon;
    public Weapon fist;
    public ClassLogic charClass;

    public InventorySystem inventory;
    public EquipmentSystem equipment;

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
            Math.round(agility / 10),
            1,
            10,
            "punches",
            false,
            null,
            null,
            0,
            WeaponTypes.STAFF,
            Handed.ONE_HANDED
        );
        this.charClass = charClass;
        this.equipment = new EquipmentSystem();
        this.inventory = new InventorySystem(new ArrayList<Item>());
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

    // public void equipWeapon(Weapon weapon){
    //     // this.equippedWeapon = weapon;
    //     this.equipment.equipItem()
    // }
    //
    public String equip(Item item){
        String x = "yea";

        if(!item.equippable()){
            return StringUtils.format("%s is not an equippable item!", item.name);
        }
        if(item.canEquip(charClass)){
            this.equipment.equipItem(item);
            return StringUtils.format("%s equipped!", item.name);
        }

        return StringUtils.format("%s cannot use a %s", charClass.getName(), item.name);

    }

    public String addToInventory(Item item){
        if(inventory.enoughSpace()){
            inventory.addToInventory(item);
            return StringUtils.format("%s added", item.name);
        }
        return StringUtils.format("inventory full!");
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
        // if(
        //     equippedWeapon != null
        // ){
        //     return equippedWeapon;
        // } else {
        //     return fist;
        // }
        if(
            equipment.getWeapon()!= null
        ) {
            return equipment.getWeapon();
        } else {
            return fist;
        }
    }

    @Override
    public int getDefense() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public boolean playerAligned() {
        return true;
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
        if(mp - amount < maxMP){
            mp = 0;
            // log something here this should never happen
        } else{
            mp = mp - amount;
        }
    }
}
