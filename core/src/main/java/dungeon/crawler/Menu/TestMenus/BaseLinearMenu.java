package dungeon.crawler.Menu.TestMenus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.utils.Array;

import dungeon.crawler.GameConstants;
import dungeon.crawler.Observers.ScreenChangeObserver;
// TODO: Consider passing the stage in here, then using it to spawn submenus
public class BaseLinearMenu extends Table {

    protected Array<TextButton> buttonList;
    protected ArrayList<ScreenChangeObserver> screenChangeObservers;
    protected Stage uiStage;
    protected Skin skin;

    public BaseLinearMenu(
        Skin skin,
        Stage uiStage
    ){
        super(skin);
        this.skin = skin;
        this.uiStage = uiStage;
        Drawable background = skin.getDrawable("default-round"); 
        this.buttonList = new Array<TextButton>();
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

    public void addButton(String buttonName, ChangeListener listener){
        TextButton newButton = new TextButton(buttonName, skin);

        this.add(newButton).row();
        this.addListener(listener);
    }

    /* call after all button logic is added */
    public Array<TextButton> populateButtonList(){

        Array<TextButton> newList = new Array<TextButton>();
        for(Actor actor:this.getChildren()){
            if(actor instanceof TextButton){
                newList.add((TextButton) actor);
            }
        }
        return newList;
    }

    public void advanceMenuSelection(int step){
        Actor focusedItem = getStage().getKeyboardFocus();
        
        // int activeItem
        int currentIndex = buttonList.indexOf((TextButton)focusedItem, true);
        if(currentIndex == -1){
            Gdx.app.log("Menu", "Error: index is outside buttonList");
            getStage().setKeyboardFocus(buttonList.get(0));
            return;
        }

        int newIndex = currentIndex + step;
        if (0 <= newIndex && newIndex < buttonList.size){

            getStage().setKeyboardFocus(buttonList.get(newIndex));
        }

    }

    // public TextButton returnActiveButton(){

    //     for(TextButton button: buttonList){
    //         if(button instanceo(TextButton)getStage().getKeyboardFocus()){
                
    //         }
    //     }
    // }

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
