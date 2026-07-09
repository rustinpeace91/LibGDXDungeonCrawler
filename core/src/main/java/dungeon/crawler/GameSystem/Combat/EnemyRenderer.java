package dungeon.crawler.GameSystem.Combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dungeon.crawler.Data.Enemies.EnemySpriteRegistry;
import dungeon.crawler.GameSystem.GameState.GameState;

public class EnemyRenderer {

    private GameState gameState;
    private Stage enemyStage;
    private EnemySpriteRegistry enemySpriteRegistry;
    public EnemyRenderer(
        GameState gameState,
        Stage enemyStage
    ) {
        this.gameState = gameState;
        this.enemyStage = enemyStage;
        this.enemySpriteRegistry = new EnemySpriteRegistry();
    }


    public void intializeEnemySprites() {
        this.enemyStage.clear();
        // use EnemySprites reference
        // put Sprites into TextureRegion and add to an array
        // seperate Enemy AnimatedSprite class?
    }

    public void draw(SpriteBatch batch){
        // batch.draw(animation.getFrame, actor.getX(), actor.getY)

    }

    public void updateEnemySprites(){
        // take stock of enemyRoster in game stage
        // remove and dispose of any sprites that do not have an Enemy available to them
    }


    public void dispose(){
        // dispose of all assets here
    }



}
