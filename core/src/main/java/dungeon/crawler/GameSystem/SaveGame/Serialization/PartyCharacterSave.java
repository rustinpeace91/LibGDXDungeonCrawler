package dungeon.crawler.GameSystem.SaveGame.Serialization;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.Stance;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.EquipmentSlot;

import java.util.ArrayList;
import java.util.Map;

public class PartyCharacterSave {
    public int level;
    public int xp;
    public int strength;
    public int agility;
    public int intelligence;
    public int perception;
    public boolean isHero;
    public int toHit;
    public String charClass;
    /* can we use an enum for the key instead*/
    public Map<EquipmentSlot, String> equipment;

    public int maxHp;
    public int maxMP;
    public int hp;
    public int mp;
    public Stance stance;
    public ArrayList<Condition> conditions;
    public boolean isDead;

    /* will be added to empty Inventory after character initialization */
    public ArrayList<String> inventory;

    /* will be tacked onto charClass MagicSystem */
    public ArrayList<String> spells;

    public PartyCharacterSave(){};


}
