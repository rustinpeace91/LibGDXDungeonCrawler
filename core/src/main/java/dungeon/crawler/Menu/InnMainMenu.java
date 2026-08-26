package dungeon.crawler.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Observers.StatusMenuObserver;
import dungeon.crawler.Menu.Overworld.Inventory.*;
import dungeon.crawler.Observers.MenuInputObserver;
import dungeon.crawler.Observers.ScreenChangeObserver;
import dungeon.crawler.Screens.InnScreen;
import dungeon.crawler.Screens.ShopScreen;
import dungeon.crawler.Utils.Formulas;
import dungeon.crawler.Utils.PartyUtils;
import dungeon.crawler.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InnMainMenu extends BaseLinearMenu implements OverworldSubMenu{
    private final List<MenuInputObserver> listeners = new ArrayList<>();
    protected final GameState gameState;
    private final int price;
    public BaseLinearMenu asCombatMenu(){return this;}

    public InnMainMenu (
        Skin skin,
        InnScreen shopscreen,
        GameState gameState,
        int price
    ) {
        super(
            skin
        );
        this.gameState = gameState;
        this.price = price;
        setTitle("Inn.\n" + price + " gold per night");
        this.addButton("Sleep", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(gameState.gold >= price){
                    shopscreen.handleSleep();
                } else {
                    showPopup("Not enough gold!", 2f);
                }
                returnToParentMenu();
            }
        });


        this.addButton("Leave", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                shopscreen.exitShop();
            }
        });

        this.pack();
        this.addFocusListeners();
        this.setPosition(10, Gdx.graphics.getHeight() - this.getHeight() - 50);

        // addMenuListeners(partyButton, searchButton, testNewMenu);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);

        if (stage != null) {
            refreshAndSetActive();
        }
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




    @Override
    public void openSubMenu(BaseLinearMenu nextMenu){
        super.openSubMenu(nextMenu);
        this.setVisible(true);
    }

}
