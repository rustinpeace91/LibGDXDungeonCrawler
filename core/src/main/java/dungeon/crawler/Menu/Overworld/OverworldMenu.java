package dungeon.crawler.Menu.Overworld;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Toggleable;
import dungeon.crawler.Observers.MenuInputObserver;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class OverworldMenu extends BaseLinearMenu implements Toggleable {
    private final List<MenuInputObserver> listeners = new ArrayList<>();
    protected final GameState gameState;

    public OverworldMenu (
        Skin skin,
        GameState gameState
    ) {
        super(
            skin
        );
        this.gameState = gameState;
        setToggleable(true);
    // TODO: Make into a for loop?
        // TextButton inventoryButton = new TextButton("inventory", skin);
        // TextButton statusButton = new TextButton("Status", skin);
        // TextButton partyButton = new TextButton("Party", skin);
        // TextButton searchButton = new TextButton("Options", skin);
        // // BAD! make a fucking for loop ffs!
        // this.buttonList.add(inventoryButton);
        // this.buttonList.add(statusButton);
        // this.buttonList.add(partyButton);
        // this.buttonList.add(searchButton);



        // this.add(inventoryButton).row();
        // this.add(statusButton).row();

        // this.add(partyButton).row();
        // this.add(searchButton).row();
        this.addButton("Inventory", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){

            }
        });

        this.addButton("Status", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                StatusSelectionMenu newMenu = new StatusSelectionMenu(
                    skin,
                    gameState
                );
                setSubMenu(newMenu);
                openSubMenu(newMenu);

            }
        });

        this.addButton("Options", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){

            }
        });

        this.pack();
        this.addFocusListeners();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        // addMenuListeners(partyButton, searchButton, testNewMenu);
    }

    // public final void addMenuListeners(
    //     TextButton partyButton,
    //     TextButton searchButton,
    //     TextButton testNewMenu
    // ){
    //     // testNewMenu.addListener(new ChangeListener(){
    //     //     @Override
    //     //     public void changed(ChangeEvent event, Actor actor) {
    //     //         notifyScreenChange(GameConstants.GAME_SCREEN.TEST_SCREEN);
    //     //     }
    //     // });
    // }

    @Override
    protected void setStage(Stage stage) {
        // TODO: Move this logic OUTTA here. This runs when the menu closes too
        super.setStage(stage);
        if(stage == null) return;
        setMenuVisibility(false);
        this.addFocusListeners();
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

    public void addListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.remove(listener);
        }
    }

    public void notifyOnMenuToggled(boolean showMenu) {
        for (MenuInputObserver listener : listeners) {
            listener.onMenuToggled(showMenu);
        }
    }

    @Override
    public void openSubMenu(BaseLinearMenu nextMenu){
        super.openSubMenu(nextMenu);
        this.setVisible(true);
    }

}
