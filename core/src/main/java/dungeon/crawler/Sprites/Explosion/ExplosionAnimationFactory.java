package dungeon.crawler.Sprites.Explosion;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dungeon.crawler.Data.Enemies.EnemySpriteRegistry;
import dungeon.crawler.GameConstants;
import dungeon.crawler.Sprites.AnimationBuilder;

public class ExplosionAnimationFactory {
    private final int SPRITE_ROWS = 1;
    private final int SPRITE_COLUMNS = 16;

    public Texture sheet;

    public ExplosionAnimationFactory(){
        this.sheet = new Texture(GameConstants.EXPLOSION_SPRITES);
    }

    public Animation<TextureRegion> create(){

//        Animation<TextureRegion> explosionAnimation = AnimationBuilder.createAnimation(
//            sheet,
//            SPRITE_ROWS,
//            SPRITE_COLUMNS,
//            0.10f
//        );

        // Define the frame width and height based on your new image size
        // Example: if sheet is 600px wide with 6 frames, frameWidth is 100
        // THIS IS THE CHEAP WAY OUT. do NOT do it this way. Figure out what is wrong with your createAnimation method and fucking FIX IT
        int totalFramesCount = 16; // 👈 Set this to your exact frame count
        int frameWidth = sheet.getWidth() / totalFramesCount;
        int frameHeight = sheet.getHeight(); // It's only 1 row, so height matches sheet height

        // Split the sheet into a grid [rows][columns]
        TextureRegion[][] grid = TextureRegion.split(sheet, frameWidth, frameHeight);

        // Because it's a single row, all your frames live inside row 0
        TextureRegion[] frames = grid[0];

        // Pack into the LibGDX Animation utility (0.05 seconds per frame)
        Animation<TextureRegion> explosionAnimation = new Animation<TextureRegion>(0.07f, frames);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL); // Plays exactly once
        return explosionAnimation;
    }

    public void dispose(){
        sheet.dispose();
    }


}
