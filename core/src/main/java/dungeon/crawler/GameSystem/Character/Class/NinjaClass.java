package dungeon.crawler.GameSystem.Character.Class;


import dungeon.crawler.Data.Spells.SpellNames;
import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;
import dungeon.crawler.GameSystem.Magic.MagicSystem;

import java.util.*;

import static dungeon.crawler.GameConstants.PLAYER_STATS.AGILITY;
import static dungeon.crawler.GameConstants.PLAYER_STATS.INTELLIGENCE;
import static dungeon.crawler.GameConstants.PLAYER_STATS.PERCEPTION;
import static dungeon.crawler.GameConstants.PLAYER_STATS.STRENGTH;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes.*;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes.HEAVY;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes.*;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes.LONGBOW;

public class NinjaClass implements ClassLogic{
    private String name;
    private CharClassID id;
    public NinjaClass(){

        this.name = "Ninja";
        this.id = CharClassID.NINJA;
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnBaseStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();
        statMap.put(STRENGTH, 12);
        statMap.put(AGILITY, 14);
        statMap.put(INTELLIGENCE, 6);
        statMap.put(PERCEPTION, 12);
        return statMap;
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnLevelUpStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();

        Random random = new Random();
        statMap.put(STRENGTH, random.nextInt(2) + 1);
        statMap.put(AGILITY, random.nextInt(2) + 1);
        statMap.put(INTELLIGENCE, 0);
        statMap.put(PERCEPTION, random.nextInt(2) + 1);

        GameConstants.PLAYER_STATS[] otherStats = new  GameConstants.PLAYER_STATS[]{
            INTELLIGENCE, PERCEPTION
        };


        int index = random.nextInt(otherStats.length);
        statMap.put(otherStats[index], 1);
        return statMap;

    }

    @Override
    public int getBaseMP() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLevelUpMP() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBaseHP() {
        // TODO Auto-generated method stub
        return 18;
    }

    @Override
    public int getLevelUpHP() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public MagicSystem getMagicSystem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isMagicUser() {
        // TODO Auto-generated method stub
        return false;
    }@Override
    public boolean canSteal() {
        return true;
    }

    @Override
    public void fillSpells(int level) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<SpellNames> getSpellNames() {
        return null;
    }

    @Override
    public ArrayList<ArmorTypes> getArmorRestrictions() {
        ArmorTypes[] types = {
            BASIC
        };
        ArrayList<ArmorTypes> typeList = new ArrayList<ArmorTypes>(Arrays.asList(types));
        return typeList;
    }

    @Override
    public ArrayList<WeaponTypes> getWeaponRestrictions() {
        WeaponTypes[] types = {
            STAFF
        };
        ArrayList<WeaponTypes> typeList = new ArrayList<WeaponTypes>(Arrays.asList(types));
        return typeList;
    }
}
