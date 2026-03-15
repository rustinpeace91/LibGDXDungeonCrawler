package dungeon.crawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dungeon.crawler.Observers.ScreenChangeObserver;

public class MainGame extends Game implements ScreenChangeObserver {
	SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
    	setScreen(new WorldScreenRefactor(
    		this,
    		spriteBatch,
    		9f,
    		21f,
    		"Maps/overworld.tmx",
    		true
    	));

		// setScreen(new CombatScreen(this));

//    	setScreen(new MenuScreen());
    }
	
	@Override
	public void onScreenChange(GameConstants.GAME_SCREEN screen){
		setScreen(new CombatScreen(this));
	}

}
