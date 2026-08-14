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
import dungeon.crawler.Menu.OverworldSubMenu;

import java.util.Arrays;

public class ShopItemPurchaseMenu extends BaseLinearMenu implements OverworldSubMenu {
    private final GameState gameState;
    private final Item selectedItem;


    public ShopItemPurchaseMenu(
        Skin skin,
        GameState gameState,
        Item selectedItem
    ){
        super(skin);
        this.gameState = gameState;
        this.selectedItem = selectedItem;
        this.initializeButtons();
    }

    public BaseLinearMenu asCombatMenu(){return this;}


    protected void updateButtons(){

        this.clearChildren();
        String title = selectedItem.getName() + "\n" +
            selectedItem.value;

        setTitle(title);
        this.initializeArrow();


        if(Arrays.asList(GameConstants.USABLE_ITEMS).contains(selectedItem.returnItemType())){
            this.addButton("Buy",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
//                        ItemUtils.useItem(currentCombatant, selectedItem);
//                        showPopup(StringUtils.format(
//                            "%s used a %s",
//                            currentCombatant.getName(), selectedItem.getName()
//                        ), 1f);
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

        if(getStage() == null) {
            Gdx.app.log("Menu Error", "refreshAndSetActive called BEFORE linear menu added to stage");
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
        returnToParentMenu();
    }

    @Override
    protected void setStage(Stage stage) {

        super.setStage(stage);

        if(parentMenu != null){

            setSizeandPosition(GameConstants.SUBMENU_SIZE.TALL);
        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }

    public void handleUseAction(Item item, int targetId){
        returnToParentMenu();
    }
}
