package dungeon.crawler.Sprites.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import dungeon.crawler.Data.Enemies.EnemySpriteParams;
import dungeon.crawler.Data.Enemies.EnemySpriteRegistry;

import java.util.HashMap;
import java.util.Map;

public class EnemyAnimatedSpriteFactory
{
    private EnemySpriteRegistry enemySpriteRegistry;

    public EnemyAnimatedSpriteFactory(){
        this.enemySpriteRegistry = new EnemySpriteRegistry();
    }

    public EnemyAnimatedSprite create(
        String enemyID,
        float x,
        float y,
        float sx,
        float sy
    ){
        EnemySpriteParams params = this.enemySpriteRegistry.getEnemySpriteMap().get(enemyID);
        Map<String, Animation<TextureRegion>> animations = new HashMap<>();
        // TODO: Move these into AssetManager to prevent memory leaks
        // AssetManager.load(f1)(f2)

        Texture f1 = new Texture(params.getFrame1());
        Texture f2 = new Texture(params.getFrame2());
        TextureRegion region1 = new TextureRegion(f1);
        TextureRegion region2 = new TextureRegion(f2);
        // TODO: create a dispose method to get rid of all assets
        // AssetManager.dispose()
        // TODO: move speed to gameConstant
        Animation<TextureRegion> animation = new Animation<>(0.5f, region1, region2);
        animations.put("idle", animation);
        return new EnemyAnimatedSprite(
            enemyID,
            animations,
            "idle",
            x,
            y,
            sx,
            sy
        );
    }

}
