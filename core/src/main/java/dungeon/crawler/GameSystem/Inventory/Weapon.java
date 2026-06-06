package dungeon.crawler.GameSystem.Inventory;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Combat.Elemental;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;


public class Weapon extends Item{
    public int toHit;
    public int damageLow;
    public int damageHigh;
    public String flavorTextVerb;
    public boolean ranged;
    public Condition condition;
    public Elemental elemental;
    public Weapon(
        String name,
        PartyCharacter owner,
        int toHit,
        int damageLow,
        int damageHigh,
        String flavorTextVerb,
        boolean ranged,
        Condition condition,
        Elemental elemental
    ) {
        super(name, owner);
        this.toHit = toHit;
        this.damageLow = damageLow;
        this.damageHigh = damageHigh;
        this.flavorTextVerb = flavorTextVerb;
        this.ranged = ranged;
        this.condition = condition;
        this.elemental = elemental;
//        this.classRestrictions = classRestrictions;
    }
//    public ArrayList<CharacterClass> classRestrictions;


}
