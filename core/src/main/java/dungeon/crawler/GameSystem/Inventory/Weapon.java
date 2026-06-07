package dungeon.crawler.GameSystem.Inventory;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Combat.Elemental;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.Handed;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;


public class Weapon extends Item{
    public int toHit;
    public int damageLow;
    public int damageHigh;
    public String flavorTextVerb;
    public boolean ranged;
    public Condition condition;
    public Elemental elemental;
    public WeaponTypes weaponType;
    public Handed handed;
    public Weapon(
        String name,
        PartyCharacter owner,
        int toHit,
        int damageLow,
        int damageHigh,
        String flavorTextVerb,
        boolean ranged,
        Condition condition,
        Elemental elemental,
        int value,
        WeaponTypes weaponType,
        Handed handed
    ) {
        super(
            name,
            owner,
            value,
            ItemType.WEAPON
        );
        this.toHit = toHit;
        this.damageLow = damageLow;
        this.damageHigh = damageHigh;
        this.flavorTextVerb = flavorTextVerb;
        this.ranged = ranged;
        this.condition = condition;
        this.elemental = elemental;
        this.weaponType = weaponType;
        this.handed = handed;
    }
//    public ArrayList<CharacterClass> classRestrictions;
//

    public boolean canEquip(ClassLogic charClass){
        return charClass.getWeaponRestrictions().contains(weaponType);
    }


}
