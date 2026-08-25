package dungeon.crawler.GameSystem.Leveling;

import com.badlogic.gdx.utils.IntIntMap;

public class LevelTable {
    private static final IntIntMap table = new IntIntMap();
    // The baseline constants for linear scaling
    private static final int BASE_LEVEL = 12;
    private static final long XP_AT_LEVEL_12 = 204700L;
    private static final long LINEAR_STEP = 100000L; // Flat XP added per level after 12

    static {
        table.put(1, 0);
        table.put(2, 100);
        table.put(3, 250);
        table.put(4, 550);
        table.put(5, 1150);
        table.put(6, 2350);
        table.put(7, 4750);
        table.put(8, 9550);
        table.put(9, 19150);
        table.put(10, 38350);
        table.put(11, 76750);
        table.put(12, 153550);
    }

    public static int getRequiredXp(int level) {
        return table.get(level, 999999);
    }

    public static long getRequiredXPForLevel(int targetLevel) {
        if (targetLevel <= 1) {
            return 0;
        }

        if (targetLevel < BASE_LEVEL) {
            return table.get(targetLevel, 9999999);
        }

        long levelsAbove12 = targetLevel - BASE_LEVEL;
        return XP_AT_LEVEL_12 + (levelsAbove12 * LINEAR_STEP);
    }
}
