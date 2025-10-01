package dungeon.crawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {
	SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
    	setScreen(new WorldScreen(
    		this,
    		spriteBatch
    	));

//    	setScreen(new WalkExample(
//    		this
//    	));
    }

}
