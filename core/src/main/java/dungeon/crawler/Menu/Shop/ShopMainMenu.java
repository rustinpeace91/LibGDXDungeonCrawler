package dungeon.crawler.Menu.Shop;

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
import dungeon.crawler.Screens.ShopScreen;
import dungeon.crawler.Utils.Formulas;

import java.util.ArrayList;
import java.util.List;

public class ShopMainMenu extends BaseLinearMenu{
    private final List<MenuInputObserver> listeners = new ArrayList<>();
    protected final GameState gameState;
    private final ArrayList<Item> shopInventory;
    private final StatusMenuObserver statusMenuObserver;

    public ShopMainMenu (
        Skin skin,
        ShopScreen shopscreen,
        GameState gameState,
        ArrayList<Item> shopInventory
    ) {
        super(
            skin
        );
        this.gameState = gameState;
        this.shopInventory = shopInventory;
        this.statusMenuObserver = new StatusMenuObserver();
        this.addButton("Buy", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                ShopCharSelectMenu newMenu = new ShopCharSelectMenu(
                    skin,
                    gameState,
                    shopInventory
                );
                setSubMenu(newMenu);
                openSubMenu(newMenu);
            }
        });
        this.addButton("Sell", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                InventoryCharSelectMenu newMenu = new InventoryCharSelectMenu(
                    skin,
                    gameState,
                    false
                );
                newMenu.setCanSellItems(true);
                setSubMenu(newMenu);
                openSubMenu(newMenu);
            }
        });
        this.addButton("Party Inventory", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                InventoryCharSelectMenu newMenu = new InventoryCharSelectMenu(
                    skin,
                    gameState,
                    false
                );
                setSubMenu(newMenu);
                openSubMenu(newMenu);
            }
        });
        if(Formulas.partyCanSteal(gameState.party)){
            this.addButton("Steal", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    shopscreen.handleSteal();
                }
            });
        }

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
