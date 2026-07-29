package dungeon.crawler.Menu.Combat.Magic;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellNames;
import dungeon.crawler.Data.Spells.SpellRegistry;
import dungeon.crawler.Data.Spells.SpellType;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Combat.CombatMenu;

public class SpellSelectMenu extends BaseLinearMenu{

    private GameState gameState;
    private CombatMenu combatMenu;
    private Combatant currentCombatant;
    private final ArrayList<SpellNames> spellList;

    public SpellSelectMenu(
        Skin skin,
        GameState gameState,
        Combatant currentCombatant,
        ArrayList<SpellNames> spellList

    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.spellList = spellList;
        this.attackButtons();
    }


    protected void attackButtons(){

        // redundant for loops here are fine. There won't be more than 5 enemies max
        Map<Integer, Combatant> availableCombatants = CombatUtils.returnAliveCombatants(
            this.gameState.currentEnemyRoster
        );
        SpellRegistry spellRegistry = SpellRegistry.INSTANCE;
        for (SpellNames spellID : spellList) {
//            Integer id = entry.getKey();
//            Combatant c = entry.getValue();
            Spell spell = spellRegistry.get(spellID);
            String buttonName = spell.getName() + "/" + String.valueOf(spell.getCost()) + " MP";
            this.addButton(buttonName,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
//                        returnToParentMenu();
//                        combatMenu.handleAttackSelection(id);
                        // IF Spell AOE, cast, else show target select menu
                        if(spell.getType() == SpellType.AOE_DEFENSE || spell.getType() == SpellType.AOE_OFFENSE){
                            returnToParentMenu();
                            combatMenu.handleCastAction(spellID, -1);
                        } else {
                            BaseLinearMenu nextMenu = new SpellTargetSelectMenu(
                                skin,
                                gameState,
                                spell,
                                SpellSelectMenu.this
                            );
                            setSubMenu(nextMenu);
                            openSubMenu(nextMenu);
                        }
                    }
                }
            );

        }
    }

    @Override
    protected void setStage(Stage stage) {

        super.setStage(stage);
        if(parentMenu != null){
            float wif = this.getWidth();

            combatMenu = (CombatMenu)parentMenu;
            this.defaults().pad(2);

            this.pack();
            this.setPosition(parentMenu.getStage().getWidth() - this.getWidth(), this.parentMenu.getOriginY());
        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }

    public void handleCastAction(SpellNames spellId, int targetId){
        returnToParentMenu();
        combatMenu.handleCastAction(spellId, targetId);
    }

    // spawn menu
    // take in GameState as parametera
    // spin up button for each enemy able to attack (make extra function for that?)
    // CombatUtils.returnCombatElegableCombatants(this.gameState.enemyRoster);
    // on button Press, notify CombatMenu of attack selection

}
