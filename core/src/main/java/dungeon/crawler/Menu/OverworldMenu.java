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
        TextButton testNewMenu = new TextButton("Test New Menu", skin);
        // BAD! make a fucking for loop ffs!
        this.buttonList.add(inventoryButton);
        this.buttonList.add(statusButton);
        this.buttonList.add(partyButton);
        this.buttonList.add(searchButton);
        this.buttonList.add(testNewMenu);


        
        this.add(inventoryButton).row();
        this.add(statusButton).row();
        
        this.add(partyButton).row();
        this.add(searchButton).row();
        this.add(testNewMenu).row();
        
        this.pack();
        this.addFocusListeners();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        addMenuListeners(partyButton, searchButton, testNewMenu);
    }

    public final void addMenuListeners(
        TextButton partyButton,
        TextButton searchButton,
        TextButton testNewMenu
    ){
        testNewMenu.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                notifyScreenChange(GameConstants.GAME_SCREEN.TEST_SCREEN);
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
