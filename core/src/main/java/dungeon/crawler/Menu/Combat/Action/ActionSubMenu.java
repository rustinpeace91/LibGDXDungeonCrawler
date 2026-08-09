package dungeon.crawler.Menu.Combat.Action;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.utils.Align;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Combat.CombatAction;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.Combat.CombatMenu;

public class ActionSubMenu extends BaseLinearMenu{

    private GameState gameState;
    private CombatMenu combatMenu;
    private PartyCharacter currentCombatant;

    public ActionSubMenu(
        Skin skin,
        GameState gameState,
        PartyCharacter currentCombatant

    ){
        super(skin);
        this.gameState = gameState;
        this.currentCombatant = currentCombatant;
        this.attackButtons();
    }


    protected void attackButtons(){
        this.defaults().size(190f, 60f).pad(5f);

        // redundant for loops here are fine. There won't be more than 5 enemies max
        ArrayList<CombatActionState> entryavailableActions = CombatUtils.returnAvailableActions(
            currentCombatant
        );

        for (CombatActionState entry : entryavailableActions) {
            String buttonName = entry.toString();
            this.addButton(buttonName,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        returnToParentMenu();
                        combatMenu.handleActionSelection(entry);
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

    // spawn menu
    // take in GameState as parametera
    // spin up button for each enemy able to attack (make extra function for that?)
    // CombatUtils.returnCombatElegableCombatants(this.gameState.enemyRoster);
    // on button Press, notify CombatMenu of attack selection

}
