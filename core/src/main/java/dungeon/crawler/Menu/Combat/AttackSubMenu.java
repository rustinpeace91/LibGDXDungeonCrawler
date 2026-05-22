package dungeon.crawler.Menu.Combat;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
            this.setPosition(Gdx.graphics.getWidth() - (this.parentMenu.getWidth() + 50), this.parentMenu.getOriginY());
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
