package dungeon.crawler.Player;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dungeon.crawler.Sprites.AnimatedSprite;

public class PlayerAnimatedSprite extends AnimatedSprite{

	public PlayerAnimatedSprite(Map<String, Animation<TextureRegion>> animations, String initialState, float x, float y,
			float sx, float sy) {
		super(animations, initialState, x, y, sx, sy);
		// TODO Auto-generated constructor stub
	}
	

}
