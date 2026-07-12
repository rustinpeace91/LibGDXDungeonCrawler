package dungeon.crawler.GameSystem.Combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dungeon.crawler.Data.Enemies.EnemySpriteRegistry;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Enemy;
import dungeon.crawler.GameSystem.Character.EnemyCombatant;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Sprites.Enemy.EnemyAnimatedSprite;
import dungeon.crawler.Sprites.Enemy.EnemyAnimatedSpriteFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EnemyRenderer {

    private float worldHeight;
    private float worldWidth;
    private GameState gameState;
    private Stage enemyStage;
    private EnemySpriteRegistry enemySpriteRegistry;
    private Map<Integer, EnemyAnimatedSprite> enemySpriteRoster;
    private EnemyAnimatedSpriteFactory enemySpriteFactory;
    public EnemyRenderer(
        GameState gameState,
        Stage enemyStage
    ) {
        this.gameState = gameState;
        this.enemyStage = enemyStage;
        this.enemySpriteRegistry = new EnemySpriteRegistry();
        this.enemySpriteRoster = new HashMap<>();
        this.enemySpriteFactory = new EnemyAnimatedSpriteFactory();
        this.worldWidth = enemyStage.getViewport().getWorldWidth();
        this.worldHeight = enemyStage.getViewport().getWorldHeight();
    }


    public void intializeEnemySprites() {
        enemyStage.clear();
        for (Map.Entry<Integer, EnemyCombatant> enemyEntry: gameState.currentEnemyRoster.entrySet()){
            EnemyCombatant enemy = enemyEntry.getValue();
            EnemyAnimatedSprite newSprite = enemySpriteFactory.create(
                enemy.identifier,
                0f,
                0f
            );
            // + last sprites width?
            float xCoord = worldWidth * 0.45f;
            if(enemyEntry.getKey() > 1){
                xCoord = xCoord + 100f;
            }

            newSprite.getSprite().setPosition(
                xCoord, worldHeight * 0.15f
            );
            enemySpriteRoster.put(enemyEntry.getKey(), newSprite);
        }
    }

    public void draw(SpriteBatch batch){
        // batch.draw(animation.getFrame, actor.getX(), actor.getY)
        for (Map.Entry<Integer, EnemyAnimatedSprite> enemyEntry: enemySpriteRoster.entrySet()){
            EnemyAnimatedSprite enemy = enemyEntry.getValue();
            enemy.render(batch);
        }

    }

    public void updateEnemySprites(){
        // use returnAliveCombatants from EnemyRoster to check for sprites that do not have
        // an alive enemy and remove them. Trigger animation?
        Set<Integer> keys = enemySpriteRoster.keySet();
        Map<Integer, Combatant> aliveEnemies = CombatUtils.returnAliveCombatants(gameState.currentEnemyRoster);

        Iterator<Map.Entry<Integer, EnemyAnimatedSprite>> it = enemySpriteRoster.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, EnemyAnimatedSprite> entry = it.next();

            if (!aliveEnemies.containsKey(entry.getKey())) {
                EnemyAnimatedSprite dyingSprite = entry.getValue();
                it.remove();
            }
        }

    }

    public void resize(int width, int height){
        enemyStage.getViewport().update(width, height, false);
        worldWidth = enemyStage.getViewport().getWorldWidth();
        worldHeight = enemyStage.getViewport().getWorldHeight();

        // TODO: reposition ALL enemies here :(
    }
    public void dispose(){
        // dispose of all assets here
        enemySpriteFactory.dispose();
    }



}
