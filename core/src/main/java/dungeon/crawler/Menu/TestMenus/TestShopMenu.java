package dungeon.crawler.Menu.TestMenus;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameConstants;
import dungeon.crawler.MainGame;
import dungeon.crawler.Menu.BaseLinearMenu;

public class TestShopMenu extends BaseLinearMenu{
    private MainGame game;
    public TestShopMenu(
        Skin skin,
        MainGame game
    ){
        super(
            skin
        );
        this.game = game;

        this.addButton("Buy", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                BaseLinearMenu nextMenu = new TestShopSubMenu(skin);
                setSubMenu(nextMenu);
                openSubMenu(nextMenu);
            }
        });

        this.addButton("Steal", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: make this a seperate screen
                Random stealSeed = new Random();
                int roll = stealSeed.nextInt(100) + game.gameState.player.agility;
                String statusText = "";
                if(roll > 100 - 20){
                    statusText = "you get away scott free";
                } else{
                    statusText = "busted! you're going to the brig m8";
                }
                showTemporaryMessage(statusText, getStage(), skin);

            }
        });

        this.addButton("Leave", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.onScreenChange(GameConstants.GAME_SCREEN.WALK_TOWN);
            }
        });



    
        this.pack();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        this.addFocusListeners();
    }

    public void showTemporaryMessage(String text, Stage stage, Skin skin) {
        // 1. Create a container that looks like a box
        Table toast = new Table(skin);
        toast.setBackground(skin.newDrawable("white", Color.BLACK)); // Solid black bg
        toast.add(new Label(text, skin)).pad(20);
        
        // 2. Position it center-screen
        toast.pack();
        toast.setPosition(Gdx.graphics.getWidth()/2f - toast.getWidth()/2f, 
                        Gdx.graphics.getHeight()/2f - toast.getHeight()/2f);

        // 3. The "Fire and Forget" logic
        toast.addAction(Actions.sequence(
            Actions.delay(2.0f),      // Stay for 2 seconds
            Actions.fadeOut(0.5f),    // Fade for half a second
            Actions.removeActor()     // Die
        ));

        stage.addActor(toast);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        
        if (stage != null) {
            setMenuVisibility(true); 
        }
    }

    @Override
    public void openSubMenu(BaseLinearMenu nextMenu){
        super.openSubMenu(nextMenu);
        this.setVisible(true);
    }

    

}
