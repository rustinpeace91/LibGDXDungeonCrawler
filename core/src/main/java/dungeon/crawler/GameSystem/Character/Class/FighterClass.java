package dungeon.crawler.GameSystem.Character.Class;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.AGILITY;
import static dungeon.crawler.GameConstants.PLAYER_STATS.INTELLIGENCE;
import static dungeon.crawler.GameConstants.PLAYER_STATS.PERCEPTION;
import static dungeon.crawler.GameConstants.PLAYER_STATS.STRENGTH;
import dungeon.crawler.GameSystem.Magic.MagicSystem;

public class FighterClass implements ClassLogic{
    private String name;
    public FighterClass(){
        this.name = "fighter";
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnBaseStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();
        statMap.put(STRENGTH, 14);
        statMap.put(AGILITY, 12);
        statMap.put(INTELLIGENCE, 6);
        statMap.put(PERCEPTION, 8);
        return statMap;
    }

    @Override
    public Map<GameConstants.PLAYER_STATS, Integer> returnLevelUpStats() {
        Map<GameConstants.PLAYER_STATS, Integer> statMap = new HashMap<>();

        Random random = new Random();
        statMap.put(STRENGTH, random.nextInt(4) + 1);
        statMap.put(AGILITY, random.nextInt(2) + 1);
        statMap.put(INTELLIGENCE, 0);
        statMap.put(PERCEPTION, 0);

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
        return 10;
    }

    @Override
    public int getLevelUpMP() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBaseHP() {
        // TODO Auto-generated method stub
        return 48;
    }

    @Override
    public int getLevelUpHP() {
        // TODO Auto-generated method stub
        return 12;
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

}
