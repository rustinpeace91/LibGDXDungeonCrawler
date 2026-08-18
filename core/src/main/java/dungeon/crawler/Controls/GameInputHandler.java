package dungeon.crawler.Controls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameInputHandler {

    public boolean held(GameKey key){
        switch (key) {
            case UP:
                return Gdx.input.isKeyPressed(Input.Keys.UP);
            case DOWN:
                return Gdx.input.isKeyPressed(Input.Keys.DOWN);
            case LEFT:
                return Gdx.input.isKeyPressed(Input.Keys.LEFT);
            case RIGHT:
                return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            case CONFIRM:
                return Gdx.input.isKeyPressed(Input.Keys.ENTER);
            case CANCEL:
                return Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
            case MENU:
                return Gdx.input.isKeyPressed(Input.Keys.TAB);
            default:
                return false;
        }
    }

    public boolean pressed(GameKey key) {
        switch (key) {
            case UP:
                return Gdx.input.isKeyJustPressed(Input.Keys.UP);
            case DOWN:
                return Gdx.input.isKeyJustPressed(Input.Keys.DOWN);
            case LEFT:
                return Gdx.input.isKeyJustPressed(Input.Keys.LEFT);
            case RIGHT:
                return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT);
            case CONFIRM:
                return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
            case CANCEL:
                return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
            case MENU:
                return Gdx.input.isKeyJustPressed(Input.Keys.TAB);
            default:
                return false;
        }
    }
}
