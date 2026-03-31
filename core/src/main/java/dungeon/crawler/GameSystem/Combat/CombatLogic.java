package dungeon.crawler.GameSystem.Combat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.CombatPhase;
import dungeon.crawler.GameSystem.TestData.EnemyCombatant;
import dungeon.crawler.GameSystem.TestData.PlayerCharacter;
import dungeon.crawler.MainGame;
import dungeon.crawler.Menu.CombatEventScreen;
import dungeon.crawler.Observers.CombatLogicObserver;

public class CombatLogic {
    public CombatPhase phase;
    public LinkedList<CombatAction> actionQueue;
    public CombatEventScreen eventScreen;
    public ArrayList<CombatLogicObserver> combatLogicObservers;
    private MainGame game;

    public CombatLogic(
        CombatEventScreen eventScreen,
        MainGame game
    ){
        this.eventScreen = eventScreen;
        this.combatLogicObservers = new ArrayList<CombatLogicObserver>();
        this.actionQueue = new LinkedList<>();
        this.game = game;
    }
    public void advanceCombat(){
        if(!eventScreen.messageQueue.isEmpty()){
            return ;
        }

        switch(phase) {
            case INTRO:
                handleState(CombatPhase.ACTIONSELECT);
                break;

            case RESOLVE_NEXT_ACTION:
                if (!actionQueue.isEmpty()) {
                    CombatAction nextAction = actionQueue.pop();
                    handleAction(nextAction);

                } else {
                    handleState(CombatPhase.ACTIONSELECT);
                }
                break;
                
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
                break;
            case ACTIONSELECT:
                Gdx.app.log("Combat", "Player Select");
                // push stuff to the queue
                // roll iniative can take place here?
                notifyOnCombatMenuFocus();
                break;

            case ACTIONSELECT_COMPLETE:
                Gdx.app.log("Combat", "Enemies Decide their action");
                notifyOnActionSelectComplete();
                decideEnemyActions();
                sortByInitiative();
                handleState(CombatPhase.INITIATIVE_COMPLETE);
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
                handleState(CombatPhase.RESOLVE_NEXT_ACTION);
                break;

            case RESOLVE_NEXT_ACTION:
                Gdx.app.log("Combat", "Handling COMBAT round");
                break;
                
        }
    }

    public void handleAction(CombatAction currentAction){
        CombatActionState aState = currentAction.action;
        switch(aState){
            // TODO: break ATTACK up into a sub state machine so messages can be displayed
            // and statuses can be updated between the message breaks
            case ATTACK:
                String damageText = "";
                // fuck this
                boolean targetDead = false;
                Gdx.app.log("Combat", "Attack Made");
                AttackDamage damage = currentAction.combatant.attack();
                int defense = currentAction.target.defend(damage);
                // TODO: Find a way to get the character name from the defendent
                if(damage.toHit > defense){
                    int damageDealt = currentAction.target.takeHit(damage);
                    damageText = String.format("hit for %s damage", String.valueOf(damageDealt));
                    targetDead = currentAction.target.checkDeath();

                } else {
                    damageText = "The attack missed!";
                    targetDead = false;
                }

                eventScreen.addMessages(new String[] {damage.flavorText, damageText});
                if(targetDead){
                    eventScreen.addMessages(new String[] {String.format("%s has died", currentAction.target.getName())});
                }
                                
                break;
            case DEFEND:
                Gdx.app.log("Combat", "Defense Made");
                eventScreen.addMessages(new String[] {"the enemy stares at you dumbfounded"});
                break;
            case HEAL:
                Gdx.app.log("Combat", "heal Made");
                break;
        }
    }

    public void addAction(
        int id,
        CombatActionState actionState,
        int targetId
    ) {
        Combatant currentCombatant = game.gameState.party.get(id);
        Combatant target = game.gameState.currentEnemyRoster.get(targetId);

        int initiative = currentCombatant.rollInitiative();

        CombatAction newAction = new CombatAction(
            id,
            initiative,
            currentCombatant,
            actionState,
            target
        );
        this.actionQueue.add(newAction);
        Gdx.app.log("Combat", "Action added to queue");
        if(id <= this.game.gameState.party.keySet().size()){
            handleState(CombatPhase.ACTIONSELECT_COMPLETE);
        }
    }

    public void addListener(CombatLogicObserver listener){
        combatLogicObservers.add(listener);
    }

    public void notifyOnCombatMenuFocus(){
        for(CombatLogicObserver listener: combatLogicObservers){
            listener.onActionMenuFocus();
        }
    }


    public void notifyOnActionSelectComplete(){
        for(CombatLogicObserver listener: combatLogicObservers){
            listener.onActionSelectComplete();
        }
    }

    private void decideEnemyActions(){
        for (int enemyID: this.game.gameState.currentEnemyRoster.keySet()){
            decideAction(
                this.game.gameState.currentEnemyRoster.get(enemyID),
                enemyID
            );
        }
    }

    private void decideAction(EnemyCombatant enemy, int id){
        // for now we're just gonna roll some dice
        Random dice = new Random();
        int roll = (dice.nextInt(20) + 1);
        CombatActionState enemyAction;
        if(roll >=5){
            enemyAction = CombatActionState.ATTACK;
        } else {            
            enemyAction = CombatActionState.DEFEND;
        }
        int initiative = enemy.rollInitiative();
        // TODO: Implement logic for selecting a target
        Combatant target = this.game.gameState.party.get(1);

        CombatAction newAction = new CombatAction(
            id,
            initiative,
            enemy,
            enemyAction,
            target
        );
        this.actionQueue.add(newAction);

    }

    private void sortByInitiative(){
        actionQueue.sort((a, b) -> Integer.compare(a.iniative, a.iniative));
    }

    // public Combatant getEnemyCombatantById(int id){
    //     for(Combatant combatant: game.gameState.currentEnemyRoster){
    //         if(combatant.id == id){

    //         }
    //     };
    // }
    // add text
    // switch phase
    // handle attack
    // store logic 
    
}
