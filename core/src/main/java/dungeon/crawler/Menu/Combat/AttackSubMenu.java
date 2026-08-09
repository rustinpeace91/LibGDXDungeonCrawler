package dungeon.crawler.Menu.Combat;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.utils.Align;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Combat.CombatUtils;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;

public class AttackSubMenu extends BaseLinearMenu{

    private GameState gameState;
    private CombatMenu combatMenu;
    public AttackSubMenu(
        Skin skin,
        GameState gameState

    ){
        super(skin);
        this.gameState = gameState;
        this.attackButtons();
    }


    protected void attackButtons(){
        this.defaults().size(190f, 60f).pad(5f);

        // redundant for loops here are fine. There won't be more than 5 enemies max
        Map<Integer, Combatant> availableCombatants = CombatUtils.returnAliveCombatants(
            this.gameState.currentEnemyRoster
        );

        for (Map.Entry<Integer, Combatant> entry : availableCombatants.entrySet()) {
            Integer id = entry.getKey();
            Combatant c = entry.getValue();
            this.addButton(c.getName(),
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        returnToParentMenu();
                        combatMenu.handleAttackSelection(id);
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
