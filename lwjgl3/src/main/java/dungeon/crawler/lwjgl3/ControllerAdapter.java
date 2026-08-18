package dungeon.crawler.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import dungeon.crawler.Controls.GameInputHandler;
import dungeon.crawler.Controls.GameKey;

public class ControllerAdapter implements ControllerListener {
    GameInputHandler inputHandler;
    public static final int PS5_D_PAD_UP = 11;
    public static final int PS5_D_PAD_DOWN = 12;
    public static final int PS5_D_PAD_LEFT = 13;
    public static final int PS5_D_PAD_RIGHT = 14;

    public static final int PS5_X_BUTTON = 0;
    public static final int PS5_O_BUTTON = 1;
    public static final int PS5_SQ_BUTTON = 2;

    public ControllerAdapter(GameInputHandler inputHandler){
        Controllers.addListener(this);
        this.inputHandler = inputHandler;
    }


    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.app.log("Controller",
            controller.getName() + " button down: " + buttonCode);

        switch (buttonCode) {
            case PS5_D_PAD_UP:
                inputHandler.notifyAction(GameKey.UP);
                break;

            case PS5_D_PAD_DOWN:
                inputHandler.notifyAction(GameKey.DOWN);
                break;

            case PS5_D_PAD_LEFT:
                inputHandler.notifyAction(GameKey.LEFT);
                break;

            case PS5_D_PAD_RIGHT:
                inputHandler.notifyAction(GameKey.RIGHT);
                break;

            case PS5_X_BUTTON:
                inputHandler.notifyAction(GameKey.CONFIRM);
                break;

            case PS5_O_BUTTON:
                inputHandler.notifyAction(GameKey.CANCEL);
                break;

            case PS5_SQ_BUTTON:
                inputHandler.notifyAction(GameKey.MENU);
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
