package dungeon.crawler.Menu.Combat.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Combat.CombatMenu;
import dungeon.crawler.Menu.ScrollableLinearMenu;
import dungeon.crawler.Menu.PagePosition;

import java.util.ArrayList;

public class ItemSelectMenu extends ScrollableLinearMenu<Item> {

    private GameState gameState;
    private CombatMenu combatMenu;
    private PartyCharacter currentCombatant;
    private final ArrayList<Item> availableItems;

    public ItemSelectMenu(
        Skin skin,
        GameState gameState,
        PartyCharacter currentCombatant,
        ArrayList<Item> availableItems
    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.availableItems = availableItems;
        this.initializeButtons();
    }



    protected void updateButtons(){
        this.defaults().size(190f, 60f).pad(5f);
        this.clearChildren();
//        this.initializeArrow();
        addScrollArrowUp();

        for (int i=pageStart; i < pageEnd; i++) {
            Item item = availableItems.get(i);
            String buttonName = item.name;
            this.addButton(buttonName,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        BaseLinearMenu nextMenu = new ItemTargetSelectMenu(
                            skin,
                            gameState,
                            item,
                            ItemSelectMenu.this
                        );
                        setSubMenu(nextMenu);
                        openSubMenu(nextMenu);
                    }
                }
            );
        }
        if(getStage() == null) {
            Gdx.app.log("Menu Error", "refreshAndSetActive called BEFORE linear menu added to stage");
            // no return. Let it break the game
        }
        setVisible(true);

        addScrollArrowDown();
        if (parentMenu != null) {
            refreshAndSetActive();
        }

    }
    protected void initializeButtons(){

        this.intializeItems(availableItems);
        updateButtons();
    }

    @Override
    protected void setStage(Stage stage) {

        super.setStage(stage);
        if(parentMenu != null){
            float wif = this.getWidth();

            combatMenu = (CombatMenu)parentMenu;
            this.defaults().size(110f, 30f).pad(5f);

            this.defaults().pad(2);

            this.pack();
            this.setOrigin(Align.topRight);

            float targetX = parentMenu.getStage().getWidth(); // Right edge of screen
            float targetY = parentMenu.getTop();
            this.setPosition(targetX, targetY, Align.topRight);
        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }

    public void handleUseAction(Item item, int targetId){
        returnToParentMenu();
        combatMenu.handleItemAction(item, targetId);
    }


}
