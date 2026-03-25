package dungeon.crawler.Menu;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;

import dungeon.crawler.GameConstants;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class BaseMenu extends Table {

    protected ArrayList<TextButton> buttonList;
    protected ArrayList<ScreenChangeObserver> screenChangeObservers;

    public BaseMenu(Skin skin){
        super(skin);
        Drawable background = skin.getDrawable("default-round"); 
        this.buttonList = new ArrayList<TextButton>();
        this.screenChangeObservers = new ArrayList<ScreenChangeObserver>();
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f); 
        this.setBackground(skin.newDrawable("default-round", semiTransparentGray));
        this.defaults().pad(10).fillX().minWidth(150); // Set default padding for all cells
    }

    public void notifyScreenChange(GameConstants.GAME_SCREEN screen){
        for (ScreenChangeObserver observer : screenChangeObservers) {
            observer.onScreenChange(screen);
        }
    }

    public void addScreenChangeObserver(ScreenChangeObserver observer){
        screenChangeObservers.add(observer);
    }

    public void addFocusListeners(){
        // change logic. TODO: move variables to constants/properties
        Color focusColor = Color.YELLOW;
        Color defaultColor = Color.WHITE;
        // Arrow button
        Texture arrowTex = new Texture("ui/arrow.png");
        Image arrow = new Image(arrowTex);
        // 1. Declare this once outside the loop to avoid memory churn
        final Vector2 pos = new Vector2();
        arrow.setSize(12, 12); 
        arrow.setVisible(false); // Hide it until something is focused
        this.addActor(arrow);
        for (Actor actor : this.getChildren()) {
            if(actor instanceof TextButton){
                TextButton button = (TextButton) actor;
                button.addListener(new FocusListener(){
                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                    TextButton button = (TextButton) actor;
                    Label label = button.getLabel();
                    if (focused) {
                        label.setColor(focusColor);
                        // Get position relative to the WHOLE screen/stage
                        // 2. MATH FIX:
                        // Get the button's position relative to the OVERWORLDMENU (this)
                        // instead of the whole Stage.
                        pos.set(button.getX(), button.getY());

                        // Position the arrow to the left of the button's local X/Y
                        arrow.setPosition(
                            pos.x - arrow.getWidth(), 
                            pos.y + (button.getHeight() - arrow.getHeight()) / 2
                        );
                        arrow.setVisible(true);
                    } else {
                        label.setColor(defaultColor);
                    }
                }
                }); 
            }
        }
    }
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }    
}
