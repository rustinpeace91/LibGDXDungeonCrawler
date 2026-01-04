package dungeon.crawler.Player;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dungeon.crawler.Sprites.AnimatedSprite;
import dungeon.crawler.Player.PlayerPositionHandler;
import dungeon.crawler.Player.PlayerDirection;
import dungeon.crawler.GameConstants;

public class PlayerAnimatedSprite extends AnimatedSprite{
	private PlayerPositionHandler playerPosition;
	

	public PlayerAnimatedSprite(
		Map<String,
		Animation<TextureRegion>> animations,
		PlayerPositionHandler playerPosition,
		String initialState,
		float x,
		float y,
		float sx,
		float sy
	) {
		super(animations, initialState, x, y, sx, sy);
		this.playerPosition = playerPosition;
	}

	@Override
	public void update(float delta) {
		Sprite sprite = super.getSprite();
		sprite.setCenter(playerPosition.x, playerPosition.y);
		if(playerPosition.isMoving) {
			super.update(delta);
		}
	}
	
	
	public void changeDirection(PlayerDirection direction) {
		String animation = GameConstants.WALK_ANIMATIONS.get(direction);
		super.setState(animation);
	}
	

}
