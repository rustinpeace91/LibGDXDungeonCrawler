package dungeon.crawler.GameSystem.Character.Class;

import java.util.Map;

import dungeon.crawler.GameConstants;

public interface ClassLogic{
    public Map<GameConstants.PLAYER_STATS, Integer> returnBaseStats();
    public Map<GameConstants.PLAYER_STATS, Integer> returnLevelUpStats();
    public String getName();
}
