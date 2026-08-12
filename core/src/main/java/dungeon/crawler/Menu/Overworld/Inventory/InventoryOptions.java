package dungeon.crawler.Menu.Overworld.Inventory;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.OverworldSubMenu;
import dungeon.crawler.Utils.ItemUtils;
import dungeon.crawler.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryOptions extends BaseLinearMenu implements OverworldSubMenu {
    private final GameState gameState;
    private final PartyCharacter currentCombatant;
    private final Item selectedItem;



    public InventoryOptions(
        Skin skin,
        GameState gameState,
        PartyCharacter currentCombatant,
        Item selectedItem
    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.selectedItem = selectedItem;
        this.initializeButtons();
    }

public BaseLinearMenu asCombatMenu(){return this;}


    protected void updateButtons(){

        this.clearChildren();
        setTitle(StringUtils.format("%s's Inventory", currentCombatant.getName()));
        this.initializeArrow();

        this.addButton("Transfer",
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                        BaseLinearMenu nextMenu = new InventoryTransferMenu(
                            skin,
                            gameState,
                            currentCombatant,
                            selectedItem
                        );
                        setSubMenu(nextMenu);
                        openSubMenu(nextMenu);
                }
            }
        );
        if(Arrays.asList(GameConstants.USABLE_ITEMS).contains(selectedItem.returnItemType())){
            this.addButton("Use",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        ItemUtils.useItem(currentCombatant, selectedItem);
                        showPopup(StringUtils.format(
                            "%s used a %s",
                            currentCombatant.getName(), selectedItem.getName()
                        ), 1f);
                        finishItemOption();
//                        BaseLinearMenu nextMenu = new InventoryTransferMenu(
//                            skin,
//                            gameState,
//                            currentCombatant,
//                            selectedItem
//                        );
//                        setSubMenu(nextMenu);
//                        openSubMenu(nextMenu);
                    }
                }
            );
        }

//        if(selectedItem.returnItemType() == Item)
        if(getStage() == null) {
            Gdx.app.log("Menu Error", "refreshAndSetActive called BEFORE linear menu added to stage");
            // no return. Let it break the game
        }
        setVisible(true);

        if (parentMenu != null) {
            refreshAndSetActive();
        }

    }
    protected void initializeButtons(){

        updateButtons();

    }

    public void finishItemOption(){
        InventoryMenu inventoryMenu = (InventoryMenu)parentMenu;
        returnToParentMenu();
        inventoryMenu.finishItemOption();
    }

    @Override
    protected void setStage(Stage stage) {

        super.setStage(stage);

        if(parentMenu != null){
//            float wif = this.getWidth();

//            combatMenu = (CombatMenu)parentMenu;
            setSizeandPosition();
        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }

    public void handleUseAction(Item item, int targetId){
        returnToParentMenu();
//        combatMenu.handleItemAction(item, targetId);
    }
}
