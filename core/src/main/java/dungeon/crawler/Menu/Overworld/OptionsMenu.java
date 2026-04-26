package dungeon.crawler.Menu.Overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;

public class OptionsMenu extends BaseLinearMenu{
    private GameState gameState;
    
    public OptionsMenu(
        Skin skin,
        GameState gameState
    ){
        super(skin);
        this.gameState = gameState;
        this.isToggleable = true;
    }
    @Override
    protected void setStage(Stage stage){
        // TODO: Move this logic OUTTA here. This runs when the menu closes too
        super.setStage(stage);
        if(stage == null) return;


        
        this.addOptionsButtons();


        float h = this.getHeight();
        if(parentMenu != null){
            this.setPosition(this.parentMenu.getOriginX() + 200, Gdx.graphics.getHeight() - (this.getHeight() + 20));
        }
        // float x = this.parentMenu.getWidth() + this.getWidth() + 40;
        // float y = Gdx.graphics.getHeight() - getHeight() - 200;
        // subStatusMenu.setPosition(
        //     x, y
        // ); 
        // stage.addActor(subStatusMenu);
        refreshAndSetActive(); 
        // this.addFocusListeners();
        this.buttonList = populateButtonList();
        this.resetMenuSelection();
    }

    protected void addOptionsButtons(){
        addButton(
            "Music",
            new ChangeListener (){
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    // nothing for now. This is for hovering
                }
            }
        );


        addButton(
            "Appearance",
            new ChangeListener (){
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    // nothing for now. This is for hovering
                }
            }
        );


        addButton(
            "Save",
            new ChangeListener (){
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    // nothing for now. This is for hovering
                }
            }
        );
        pack();
    }
}
