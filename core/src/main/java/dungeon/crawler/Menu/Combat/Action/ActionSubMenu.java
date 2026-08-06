package dungeon.crawler.Menu.Combat;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Combat.CombatAction;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;

public class ActionSubMenu extends BaseLinearMenu{

    private GameState gameState;
    private CombatMenu combatMenu;
    private Combatant currentCombatant;

    public ActionSubMenu(
        Skin skin,
        GameState gameState,
        Combatant currentCombatant

    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.attackButtons();
    }


    protected void attackButtons(){

        // redundant for loops here are fine. There won't be more than 5 enemies max
        ArrayList<CombatActionState> entryavailableActions = CombatUtils.returnAvailableActions(
            currentCombatant
        );

        for (CombatActionState entry : entryavailableActions) {

            this.addButton(entry,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        returnToParentMenu();
                        combatMenu.handleAttackSelection(entry);
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
            combatMenu = (CombatMenu)parentMenu;
            this.defaults().pad(2);

            this.pack();
        }

        if (stage != null) {
            refreshAndSetActive();
        }
    }

    // spawn menu
    // take in GameState as parametera
    // spin up button for each enemy able to attack (make extra function for that?)
    // CombatUtils.returnCombatElegableCombatants(this.gameState.enemyRoster);
    // on button Press, notify CombatMenu of attack selection

}
