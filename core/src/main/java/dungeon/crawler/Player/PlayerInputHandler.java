package dungeon.crawler.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputHandler {
	public PlayerDirection direction;
	public boolean movementInputPressed = false;
	public PlayerInputHandler(
		PlayerDirection direction
	) {
		this.direction = direction;
	}
	// handle eventual key config here
	public void updateInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			this.direction = PlayerDirection.LEFT;
			this.movementInputPressed = true;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.direction = PlayerDirection.RIGHT;
			this.movementInputPressed = true;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			this.direction = PlayerDirection.UP;
			this.movementInputPressed = true;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			this.direction = PlayerDirection.DOWN;
			this.movementInputPressed = true;
		} else {
			this.movementInputPressed = false;
		}
	}
}
