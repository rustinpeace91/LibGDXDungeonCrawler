package dungeon.crawler.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import dungeon.crawler.Controls.ControllerAdapter;
import dungeon.crawler.Controls.ControllerInputHandler;
import dungeon.crawler.Controls.GameInputHandler;
import dungeon.crawler.Controls.GameKey;

/* This whole thing is a very annoying and convoluted way of saying
    If desktop:
        - handle controller stuff
    If browser
        - do nothing
 */

public class DesktopControllerAdapter implements ControllerAdapter {
    ControllerInputHandler controllerInput;

    public DesktopControllerAdapter(){};
    @Override
    public void attach(GameInputHandler inputHandler) {
        controllerInput =  new ControllerInputHandler(inputHandler);
        Controllers.addListener(
            controllerInput
        );
    }


    @Override
    public void detach() {
        if (controllerInput != null) {
            Controllers.removeListener(controllerInput);
            controllerInput = null;
        }
    }
}
