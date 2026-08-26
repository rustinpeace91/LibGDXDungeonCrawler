package dungeon.crawler.GameSystem.Combat;

import dungeon.crawler.GameSystem.Character.EnemyCombatant;

import java.util.Map;

public class CombatStateManager {
    private Map<Integer, EnemyCombatant> currentEnemyRoster;
    private boolean finalBossFight;

    public CombatStateManager(Map<Integer, EnemyCombatant> currentEnemyRoster){
        this.currentEnemyRoster = currentEnemyRoster;
        this.finalBossFight = false;
    }

    public Map<Integer, EnemyCombatant> getCurrentEnemyRoster() {
        return currentEnemyRoster;
    }

    public boolean isFinalBossFight() {
        return finalBossFight;
    }

    public void setFinalBossFight(boolean finalBossFight) {
        this.finalBossFight = finalBossFight;
    }
}
