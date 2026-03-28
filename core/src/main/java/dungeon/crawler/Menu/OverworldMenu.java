package dungeon.crawler.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import dungeon.crawler.GameConstants;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class OverworldMenu extends BaseMenu implements Toggleable {

    public OverworldMenu (
        Skin skin
    ) {
        super(skin);
    // TODO: Make into a for loop?
        TextButton inventoryButton = new TextButton("inventory", skin);
        TextButton statusButton = new TextButton("Status", skin);
        TextButton partyButton = new TextButton("Party", skin);
        TextButton searchButton = new TextButton("Options", skin);
        TextButton testCombat = new TextButton("Test Combat", skin);
        // BAD! make a fucking for loop ffs!
        this.buttonList.add(inventoryButton);
        this.buttonList.add(statusButton);
        this.buttonList.add(partyButton);
        this.buttonList.add(searchButton);
        this.buttonList.add(testCombat);


        
        this.add(inventoryButton).row();
        this.add(statusButton).row();
        
        this.add(partyButton).row();
        this.add(searchButton).row();
        this.add(testCombat).row();
        
        this.pack();
        this.addFocusListeners();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        addMenuListeners(partyButton, searchButton, testCombat);
    }

    public final void addMenuListeners(
        TextButton partyButton,
        TextButton searchButton,
        TextButton testCombat
    ){
        testCombat.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Button", "GET READY TO FIGHT clicked");
                notifyScreenChange(GameConstants.GAME_SCREEN.COMBAT);
            }
        });
    }

    @Override
    public void notifyScreenChange(GameConstants.GAME_SCREEN screen){
            for (ScreenChangeObserver observer : screenChangeObservers) {
                observer.onScreenChange(screen);
            }
    }

    @Override
    public void addScreenChangeObserver(ScreenChangeObserver observer){
        screenChangeObservers.add(observer);
    }
}
