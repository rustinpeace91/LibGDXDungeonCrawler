package dungeon.crawler.GameSystem.Combat;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;

import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.CombatPhase;
import dungeon.crawler.Menu.CombatEventScreen;

public class CombatLogic {
    public CombatPhase phase;
    public LinkedList<CombatAction> actionQueue;
    public CombatEventScreen eventScreen;

    public CombatLogic(
        CombatEventScreen eventScreen
    ){
        this.eventScreen = eventScreen;
    }
    public void advanceCombat(){
        if(eventScreen.messageQueue.isEmpty()){
            if(phase == phase.INTRO) {
                handleState(CombatPhase.ACTIONSELECT);
            }

        }
        // if no messages
        // IF ACTIONSELECT_COMPLETE
            // advance to ENEMY_ACTION


        // if RESOLVE_NEXT_ACTION and actionQueue
            // advance action
            // perform action
            // refresh queue (remove unusable party members)
            // check for win conditions
        // if RESOLVE NEXT_ACTION and not actionQUEUE
            // advance to ACTIONSELECT
    }

    public void handleState(CombatPhase nextPhase){
        phase =  nextPhase;
        switch(nextPhase) {
            case INTRO:
                Gdx.app.log("Combat", "INTRO");
            case ACTIONSELECT:
                Gdx.app.log("Combat", "Player Select");
                // push stuff to the queue
                // roll iniative can take place here?
                break;
            case INITIATIVE_COMPLETE:
                Gdx.app.log("Combat", "Rolling for Initiative");
                // you can sort a linked list like this
                // // Sorts the party by speed (lowest to highest)
                // allCombatants.sort((a, b) -> Integer.compare(a.speed, b.speed));
            // case ENEMY_ACTION:
            //     Gdx.app.log("Combat", "ENEMIES decide their action");
            //     // enemies decide their actions
            //     // iniative is rolled for them
                break;
            case RESOLVE_NEXT_ACTION:
                Gdx.app.log("Combat", "Handling COMBAT round");
                break;
                
        }
    }

    public void handleAction(CombatAction currentAction){
        CombatActionState aState = currentAction.action;
        switch(aState){
            case ATTACK:
                Gdx.app.log("Combat", "Attack Made");
                break;
            case DEFEND:
                Gdx.app.log("Combat", "Defense Made");
                break;
            case HEAL:
                Gdx.app.log("Combat", "heal Made");
                break;
        }
    }
    // add text
    // switch phase
    // handle attack
    // store logic 
    
}
