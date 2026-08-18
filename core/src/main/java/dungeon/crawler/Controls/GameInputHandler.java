package dungeon.crawler.Controls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

public class GameInputHandler  extends InputAdapter implements ControllerListener {

    public static final int PS5_D_PAD_UP = 11;
    public static final int PS5_D_PAD_DOWN = 12;
    public static final int PS5_D_PAD_LEFT = 13;
    public static final int PS5_D_PAD_RIGHT = 14;

    public static final int PS5_X_BUTTON = 0;
    public static final int PS5_O_BUTTON = 1;
    public static final int PS5_SQ_BUTTON = 2;

    public GameInputHandler() {
        // TODO: We need to only implement this on desktop 
        Controllers.addListener(this);
    }

    private final ArrayList<GameInputObserver> listeners =
        new ArrayList<GameInputObserver>();

    public boolean held(GameKey key) {
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
                return Gdx.input.isKeyPressed(Input.Keys.BACKSPACE);
            case MENU:
                return Gdx.input.isKeyPressed(Input.Keys.E);
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
                return Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE);
            case MENU:
                return Gdx.input.isKeyJustPressed(Input.Keys.E);
            default:
                return false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                notifyAction(GameKey.UP);
                break;
            case Input.Keys.DOWN:
                notifyAction(GameKey.DOWN);
                break;
            case Input.Keys.LEFT:
                notifyAction(GameKey.LEFT);
                break;
            case Input.Keys.RIGHT:
                notifyAction(GameKey.RIGHT);
                break;
            case Input.Keys.ENTER:
                notifyAction(GameKey.CONFIRM);
                break;
            case Input.Keys.BACKSPACE:
                notifyAction(GameKey.CANCEL);
                break;
            case Input.Keys.E:
                notifyAction(GameKey.MENU);
                break;
            default:
                return false;
        }
        return true;
    }

    public void addListener(GameInputObserver listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(GameInputObserver listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    private void notifyAction(GameKey action) {
        for (GameInputObserver listener : listeners) {
            Gdx.app.log("Button", "BTTTN " + action + " pressed");
            listener.onAction(action);
        }
    }


    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.app.log("Controller",
            controller.getName() + " button down: " + buttonCode);

        switch (buttonCode) {
            case PS5_D_PAD_UP:
                notifyAction(GameKey.UP);
                break;

            case PS5_D_PAD_DOWN:
                notifyAction(GameKey.DOWN);
                break;

            case PS5_D_PAD_LEFT:
                notifyAction(GameKey.LEFT);
                break;

            case PS5_D_PAD_RIGHT:
                notifyAction(GameKey.RIGHT);
                break;

            case PS5_X_BUTTON:
                notifyAction(GameKey.CONFIRM);
                break;

            case PS5_O_BUTTON:
                notifyAction(GameKey.CANCEL);
                break;

            case PS5_SQ_BUTTON:
                notifyAction(GameKey.MENU);
                break;
            default:
                return false;

        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        Gdx.app.log("Controller",
            controller.getName() + " button up: " + buttonCode);
        return false;
    }

    @Override
    public void connected(Controller controller) {
        Gdx.app.log("Controller", "Connected: " + controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.app.log("Controller", "Disconnected: " + controller.getName());
    }

    @Override
    public boolean axisMoved(
        Controller controller,
        int axisCode,
        float value
    ) {
        return false;
    }
}
