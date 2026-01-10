package dungeon.crawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {
	SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
    	setScreen(new WorldScreenRefactor(
    		this,
    		spriteBatch,
    		11f,
    		11f,
    		GameConstants.TEST_MAP,
    		false
    	));

//    	setScreen(new MenuScreen());
    }

}
