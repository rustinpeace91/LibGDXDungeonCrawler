package dungeon.crawler.GameSystem.Character.Class;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.*;

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
    public String getName() {
        return this.name;
    }

}
