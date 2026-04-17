package dungeon.crawler.Menu;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.Observers.ActionSelectObserver;

public class CombatMenu extends BaseLinearMenu {
private final List<ActionSelectObserver> actionSelectObservers = new ArrayList<>();

    public CombatMenu (
        Skin skin
    ) {
        super(skin);
        setToggleable(false);
    // TODO: Make into a for loop?
        // TextButton fightButton = new TextButton("Fight", skin);
        // TextButton magicButton = new TextButton("Magic", skin);
        // TextButton defendButton = new TextButton("Defend", skin);
        // TextButton inventoryButton = new TextButton("Inventory", skin);
        // TextButton runButton = new TextButton("Run", skin);
        this.defaults().size(110f, 30f).pad(5f);
        // this.buttonList.add(fightButton);
        // this.buttonList.add(magicButton);
        // this.buttonList.add(defendButton);
        // this.buttonList.add(inventoryButton);
        // this.buttonList.add(runButton);

        this.addButton("Fight", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                // TODO: remove hard code
                int combatantId = 1;
                notifyActionSelect(combatantId, CombatActionState.ATTACK);
            }
        });

        this.addButton("Run", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.log("Fight", "fuuuck u");
            }
        });


        // addMenuListeners(fightButton, inventoryButton, runButton);

        // this.add(fightButton).fillX().row();
        // this.add(magicButton).row();

        // this.add(defendButton).row();
        // this.add(inventoryButton).row();
        // this.add(runButton).row();

        this.defaults().pad(2); 

        this.pack();
        this.addFocusListeners();
    }

    public final void addMenuListeners(
        TextButton fightButton,
        TextButton inventoryButton,
        TextButton runButton
    ){

        fightButton.addListener(
            new ChangeListener(){
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // TODO: remove hard code
                    int combatantId = 1;
                    notifyActionSelect(combatantId, CombatActionState.ATTACK);
                }
        });
    }


    public void notifyActionSelect(int combatantId, CombatActionState actionState){
        for (ActionSelectObserver observer : actionSelectObservers) {
            observer.onActionSelect(actionState);
        }
    }

    public void addActionSelectObserver(ActionSelectObserver observer) {
        actionSelectObservers.add(observer);
    }

    public void setActive(boolean value){
        // simimalar to setMenuVisibility except we always want to keep this menu visible
        if(value){
            this.buttonList = populateButtonList();
            resetMenuSelection();
        } else {
            getStage().setKeyboardFocus(null);
        }
    }


}
