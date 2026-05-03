package dungeon.crawler.Menu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.GameState;
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

        this.addButton("Attack", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                // TODO: remove hard code
                handleAction(CombatActionState.ATTACK);
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


        // addMenuListeners(fightButton, inventoryButton, runButton);

        // this.add(fightButton).fillX().row();
        // this.add(magicButton).row();

        // this.add(defendButton).row();
        // this.add(inventoryButton).row();
        // this.add(runButton).row();

        this.defaults().pad(2); 

        this.pack();
        this.addFocusListeners();
        this.partyIDs = new ArrayList<>(gameState.party.keySet());
        Collections.sort(this.partyIDs);
    }
    private void handleAction(CombatActionState state){
        if (currentCombatantID > 0){
            Gdx.app.log("Combat", "yeah");
        }

        if (currentCombatantID < partyIDs.size()) {
            int currentId = partyIDs.get(currentCombatantID);
            notifyActionSelect(currentId, state);
            currentCombatantID++;
        } else {
            Gdx.app.log("Combat", "Combatant ID index exceeds party size!!!!");
        }

    }


    public void notifyActionSelect(int combatantId, CombatActionState actionState){
        for (ActionSelectObserver observer : actionSelectObservers) {
            observer.onActionSelect(combatantId, actionState);
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

    // public void checkForCompletion(){
    //     if(readingMessages){
    //         readingMessages = false;
    //     }
    //     if (
    //         (currentCombatantID < partyIDs.size())
    //     ){
    //         resetMenuSelection();
    //     } else {
    //         notifyPlayerActionSelectComplete();
    //     }

    // }

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
