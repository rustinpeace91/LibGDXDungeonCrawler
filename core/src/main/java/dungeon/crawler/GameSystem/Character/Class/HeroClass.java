package dungeon.crawler.GameSystem.Character.Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.AGILITY;
import static dungeon.crawler.GameConstants.PLAYER_STATS.INTELLIGENCE;
import static dungeon.crawler.GameConstants.PLAYER_STATS.PERCEPTION;
import static dungeon.crawler.GameConstants.PLAYER_STATS.STRENGTH;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes.*;
import static dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes.*;

import dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;
import dungeon.crawler.GameSystem.Magic.MagicSystem;

public class HeroClass implements ClassLogic{
    private String name;
    public HeroClass(){
        this.name = "hero";
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnBaseStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();
        statMap.put(STRENGTH, 10);
        statMap.put(AGILITY, 10);
        statMap.put(INTELLIGENCE, 10);
        statMap.put(PERCEPTION, 10);
        return statMap;
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnLevelUpStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();

        Random random = new Random();
        statMap.put(STRENGTH, random.nextInt(2) + 1);
        statMap.put(AGILITY, 0);
        statMap.put(INTELLIGENCE, 0);
        statMap.put(PERCEPTION, random.nextInt(3) + 1);

        GameConstants.PLAYER_STATS[] otherStats = new  GameConstants.PLAYER_STATS[]{
            INTELLIGENCE, AGILITY
        };


        int index = random.nextInt(otherStats.length);
        statMap.put(otherStats[index], 1);
        return statMap;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBaseHP() {
        // TODO Auto-generated method stub
        return 30;
    }

    @Override
    public int getLevelUpHP() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public int getBaseMP() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public int getLevelUpMP() {
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
    }

    @Override
    public void fillSpells(int level) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<ArmorTypes> getArmorRestrictions() {
        ArmorTypes[] types = {
            BASIC,
            LIGHT,
            MEDIUM
        };
        ArrayList<ArmorTypes> typeList = new ArrayList<ArmorTypes>(Arrays.asList(types));
        return typeList;
    }

    @Override
    public ArrayList<WeaponTypes> getWeaponRestrictions() {
        WeaponTypes[] types = {
            SHORTSWORD,
            LONGSWORD,
            GREATSWORD,
            STAFF,
            CLUB,
            SPEAR,
            SLING,
            CROSSBOW,
            SHORTBOW
        };
        ArrayList<WeaponTypes> typeList = new ArrayList<WeaponTypes>(Arrays.asList(types));
        return typeList;
    }
}
