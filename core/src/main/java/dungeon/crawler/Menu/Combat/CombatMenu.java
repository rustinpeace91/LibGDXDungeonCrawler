package dungeon.crawler.Menu.Combat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Observers.ActionSelectObserver;

public class CombatMenu extends BaseLinearMenu {
    private final List<ActionSelectObserver> actionSelectObservers = new ArrayList<>();
    private GameState gameState;
    private List<Integer> partyIDs;
    private int currentCombatantID;
    private boolean readingMessages;

    private Actor currentActor;

    public CombatMenu (
        Skin skin,
        GameState gameState
    ) {
        super(skin);
        this.gameState = gameState;
        setToggleable(false);
        this.initializeVisualMenu();
        this.partyIDs = new ArrayList<>(gameState.party.keySet());
        Collections.sort(this.partyIDs);
    }

    private void initializeVisualMenu(){
        this.clearChildren();
        this.defaults().size(110f, 30f).pad(5f);
        this.addButton("Attack", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                // TODO: remove hard code
                // handleAction(CombatActionState.ATTACK);
                BaseLinearMenu nextMenu = new AttackSubMenu(
                    skin,
                    gameState
                );
                setSubMenu(nextMenu);
                openSubMenu(nextMenu);
            }
        });

        this.addButton("Action", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.log("Fight", "fuuuck u");
            }
        });

        this.addButton("Magic", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.log("Fight", "fuuuck u");
            }
        });

        this.addButton("Inventory", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.log("Fight", "fuuuck u");
            }
        });

        this.addButton("Run", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.log("Fight", "fuuuck u");
            }
        });

        this.defaults().pad(2); 

        this.pack();
        this.addFocusListeners();
    }
    private void handleAction(CombatActionState state, int targetId){
        if (currentCombatantID > 0){
            Gdx.app.log("Combat", "yeah");
        }

        if (currentCombatantID < partyIDs.size()) {
            int currentId = partyIDs.get(currentCombatantID);
            notifyActionSelect(currentId, state, targetId);
            currentCombatantID++;
        } else {
            Gdx.app.log("Combat", "Combatant ID index exceeds party size!!!!");
        }

    }


    public void notifyActionSelect(int combatantId, CombatActionState actionState, int targetId){
        for (ActionSelectObserver observer : actionSelectObservers) {
            observer.onActionSelect(combatantId, actionState, targetId);
        }
    }

    // public void notifyPlayerActionSelectComplete(){
    //     for (ActionSelectObserver observer : actionSelectObservers) {
    //         observer.onPlayerActionSelectComplete();
    //     }
    // }

    public void addActionSelectObserver(ActionSelectObserver observer) {
        actionSelectObservers.add(observer);
    }



    public void handleAttackSelection(int id){
        handleAction(CombatActionState.ATTACK, id);
    }

    public void resetMenu(){
        this.buttonList = populateButtonList();
        resetMenuSelection();
    }

    public void initializeMenu(){
        this.buttonList = populateButtonList();
        resetMenuSelection();
        currentCombatantID = 0;
    }

    public void setActive(boolean value){
        // simimalar to refreshAndSetActive except we always want to keep this menu visible
        if(value){
            this.buttonList = populateButtonList();
            resetMenuSelection();
        } else {
            getStage().setKeyboardFocus(null);
        }
    }



    public void setReadingMessages(boolean readingMessages) {
        this.readingMessages = readingMessages;
    }

}
