package dungeon.crawler.Controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;

public class ControllerInputHandler implements ControllerListener  {

    public static final int PS5_D_PAD_UP = 11;
    public static final int PS5_D_PAD_DOWN = 12;
    public static final int PS5_D_PAD_LEFT = 13;
    public static final int PS5_D_PAD_RIGHT = 14;

    public static final int PS5_X_BUTTON = 0;
    public static final int PS5_O_BUTTON = 1;
    public static final int PS5_SQ_BUTTON = 2;
    private final GameInputHandler inputHandler;

    public ControllerInputHandler(GameInputHandler input) {
        this.inputHandler = input;
    }
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {

        switch (buttonCode) {
            case PS5_D_PAD_UP:
                inputHandler.setControllerHeld(GameKey.UP);
                inputHandler.notifyAction(GameKey.UP);
                break;

            case PS5_D_PAD_DOWN:
                inputHandler.setControllerHeld(GameKey.DOWN);
                inputHandler.notifyAction(GameKey.DOWN);
                break;

            case PS5_D_PAD_LEFT:
                inputHandler.setControllerHeld(GameKey.LEFT);
                inputHandler.notifyAction(GameKey.LEFT);
                break;

            case PS5_D_PAD_RIGHT:
                inputHandler.setControllerHeld(GameKey.RIGHT);
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
        switch(buttonCode){
            case PS5_D_PAD_UP:
                inputHandler.clearControllerHeld();
                break;

            case PS5_D_PAD_DOWN:
                inputHandler.clearControllerHeld();
                break;

            case PS5_D_PAD_LEFT:
                inputHandler.clearControllerHeld();
                break;

            case PS5_D_PAD_RIGHT:
                inputHandler.clearControllerHeld();
                break;

        }

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
