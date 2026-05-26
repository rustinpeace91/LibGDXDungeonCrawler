package dungeon.crawler.GameSystem.Character.Class;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import dungeon.crawler.GameConstants;
import static dungeon.crawler.GameConstants.PLAYER_STATS.*;

public class HeroClass implements ClassLogic{
    public HeroClass(){

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
}
