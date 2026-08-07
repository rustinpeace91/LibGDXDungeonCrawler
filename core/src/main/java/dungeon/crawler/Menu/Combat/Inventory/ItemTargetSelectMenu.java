package dungeon.crawler.Menu.Combat.Inventory;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellType;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.Menu.BaseLinearMenu;

import java.util.Map;

public class ItemTargetSelectMenu extends BaseLinearMenu{

    private GameState gameState;
    private ItemSelectMenu itemMenu;
    private Item selectedItem;
    public ItemTargetSelectMenu(
        Skin skin,
        GameState gameState,
        Item item,
        ItemSelectMenu itemMenu
    ){
        super(skin);
        this.gameState = gameState;
        this.selectedItem = item;
        this.attackButtons();
        this.itemMenu = itemMenu;

    }


    protected void attackButtons(){

        Map<Integer, Combatant> availableCombatants = CombatUtils.returnItemUseCombatants(this.gameState.party, selectedItem);

        for (Map.Entry<Integer, Combatant> entry : availableCombatants.entrySet()) {
            Integer id = entry.getKey();
            Combatant c = entry.getValue();
            this.addButton(c.getName(),
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        returnToParentMenu();
                        itemMenu.handleUseAction(selectedItem, id);
                    }
                }
            );

        }
    }

    @Override
    protected void setStage(Stage stage) {

        super.setStage(stage);
        if(parentMenu != null){

            this.setPosition(parentMenu.getStage().getWidth() - (this.parentMenu.getWidth() + 50), this.parentMenu.getOriginY());
            this.defaults().pad(2);

            this.pack();
            this.setPosition(parentMenu.getStage().getWidth() - this.getWidth(), this.parentMenu.getOriginY());

        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }


}
