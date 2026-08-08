package dungeon.crawler.Menu.Combat.Inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellNames;
import dungeon.crawler.Data.Spells.SpellRegistry;
import dungeon.crawler.Data.Spells.SpellType;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Combat.CombatMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemSelectMenu extends BaseLinearMenu{

    private GameState gameState;
    private CombatMenu combatMenu;
    private PartyCharacter currentCombatant;
    private final List<Item> availableItems;

    public ItemSelectMenu(
        Skin skin,
        GameState gameState,
        PartyCharacter currentCombatant,
        List<Item> availableItems
    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.availableItems = availableItems;
        this.attackButtons();
    }


    protected void attackButtons(){
        this.defaults().size(170f, 60f).pad(5f);
        this.addButton("^",
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            }
        );
        for (Item item : availableItems) {

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
        this.addButton("v",
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            }
        );
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
