package dungeon.crawler.Menu.Combat.Magic;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.utils.Align;
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

        Map<Integer, Combatant> availableCombatants = CombatUtils.returnAliveCombatants(
            this.gameState.currentEnemyRoster
        );
        SpellRegistry spellRegistry = SpellRegistry.INSTANCE;
        for (SpellNames spellID : spellList) {

            Spell spell = spellRegistry.get(spellID);
            String buttonName = spell.getName() + "/" + String.valueOf(spell.getCost()) + " MP";
            this.addButton(buttonName,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

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

    public void handleCastAction(SpellNames spellId, int targetId){
        returnToParentMenu();
        combatMenu.handleCastAction(spellId, targetId);
    }



}
