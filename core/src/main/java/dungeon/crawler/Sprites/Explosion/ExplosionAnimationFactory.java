package dungeon.crawler.Sprites.Explosion;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dungeon.crawler.Data.Enemies.EnemySpriteRegistry;
import dungeon.crawler.GameConstants;
import dungeon.crawler.Sprites.AnimationBuilder;

public class ExplosionAnimationFactory {
    private final int SPRITE_ROWS = 3;
    private final int SPRITE_COLUMNS = 6;

    public Texture sheet;

    public ExplosionAnimationFactory(){
        this.sheet = new Texture(GameConstants.EXPLOSION_SPRITES);
    }

    public Animation<TextureRegion> create(){

        Animation<TextureRegion> explosionAnimation = AnimationBuilder.createAnimation(
            sheet,
            SPRITE_ROWS,
            SPRITE_COLUMNS,
            0.5f
        );
        return explosionAnimation;
    }

    public void dispose(){
        sheet.dispose();
    }


}
