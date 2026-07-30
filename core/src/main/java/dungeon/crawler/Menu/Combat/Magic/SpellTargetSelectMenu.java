package dungeon.crawler.Menu.Combat.Magic;


import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellType;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Combat.CombatMenu;

public class SpellTargetSelectMenu extends BaseLinearMenu{

    private GameState gameState;
    private SpellSelectMenu spellMenu;
    private Spell selectedSpell;
    public SpellTargetSelectMenu(
        Skin skin,
        GameState gameState,
        Spell spell,
        SpellSelectMenu spellMenu
    ){
        super(skin);
        this.gameState = gameState;
        this.selectedSpell = spell;
        this.attackButtons();
        this.spellMenu = spellMenu;

    }


    protected void attackButtons(){

        Map<Integer, Combatant> availableCombatants;
        if(
            selectedSpell.getType() == SpellType.SINGLE_OFFENSE
        ){
            availableCombatants = CombatUtils.returnAliveCombatants(
                this.gameState.currentEnemyRoster
            );
        } else if(
            selectedSpell.getType() == SpellType.RESURRECTION
        ){
            availableCombatants = CombatUtils.returnDeadCombatants(
                this.gameState.party
            );
        } else {
            availableCombatants = CombatUtils.returnAliveCombatants(
                this.gameState.party
            );
        }


        for (Map.Entry<Integer, Combatant> entry : availableCombatants.entrySet()) {
            Integer id = entry.getKey();
            Combatant c = entry.getValue();
            this.addButton(c.getName(),
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        returnToParentMenu();
                        spellMenu.handleCastAction(selectedSpell.getId(), id);
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
