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
    		spriteBatch,
    		200f,
    		200f,
    		GameConstants.TEST_MAP,
    		false
    	));

//    	setScreen(new WalkExample(
//    		this
//    	));
    }

}
