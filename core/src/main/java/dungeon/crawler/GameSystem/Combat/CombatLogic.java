package dungeon.crawler.GameSystem.Combat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Enemy;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.CombatPhase;
import dungeon.crawler.GameSystem.Leveling.LevelTable;
import dungeon.crawler.GameSystem.TestData.EnemyCombatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.MainGame;
import dungeon.crawler.Menu.CombatEventScreen;
import dungeon.crawler.Observers.CombatLogicObserver;

public class CombatLogic {
    public CombatPhase phase;
    public LinkedList<CombatAction> actionQueue;
    public CombatEventScreen eventScreen;
    public ArrayList<CombatLogicObserver> combatLogicObservers;
    public int xpGained;
    private MainGame game;

    public CombatLogic(
        CombatEventScreen eventScreen,
        MainGame game
    ){
        this.eventScreen = eventScreen;
        this.combatLogicObservers = new ArrayList<CombatLogicObserver>();
        this.actionQueue = new LinkedList<>();
        this.game = game;
        this.xpGained = 0;
    }
    public void advanceCombat(){
        /* this is run every frame and is for actions that require to wait until messages are done
        being read to run */
        if(eventScreen.isShowingMessage()){
            return ;
        }

        switch(phase) {
            case INTRO:
                notifyOnCombatMenuFocus();
                advanceState(CombatPhase.ACTIONSELECT);
                break;
            
            case ACTIONSELECT:
                // user is selecting action. Do nothing
                break;

            case ACTIONSELECT_COMPLETE:
                Gdx.app.log("Combat", "Enemies Decide their action");
                decideEnemyActions();
                sortByInitiative();
                notifyOnActionSelectComplete();
                advanceState(CombatPhase.INITIATIVE_COMPLETE);
                break;

            case INITIATIVE_COMPLETE:
                Gdx.app.log("Combat", "Rolling for Initiative");
                advanceState(CombatPhase.RESOLVE_NEXT_ACTION);
                break;

            case RESOLVE_NEXT_ACTION:
                if (!actionQueue.isEmpty()) {
                    CombatAction nextAction = actionQueue.pop();
                    handleAction(nextAction);

                } else {
                    advanceState(CombatPhase.ACTIONSELECT);
                }
                break;
            case ACTION_COMPLETE:
                advanceState(CombatPhase.CHECK_CONDITIONS);
                break;
            case CHECK_CONDITIONS:
                checkWinConditions();
                break;
            case LOSS:
                advanceState(CombatPhase.END_LOSS);
                break;
            case VICTORY:
                rewards();
                advanceState(CombatPhase.END_VICTORY);
                break;

            case END_VICTORY:
                notifyOnVictory();
                break;

            case END_LOSS:
                notifyOnLoss();
                break;

            case NEW_ROUND:
                notifyOnCombatMenuFocus();
                advanceState(CombatPhase.ACTIONSELECT);
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

    public void advanceState(CombatPhase nextPhase){
        phase =  nextPhase;

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
                Gdx.app.log("Combat", String.format(
                    "Roll: %s", String.valueOf(damage.toHit)
                ));

                if(damage.toHit > defense){
                    int damageDealt = currentAction.target.takeHit(damage);
                    damageText = String.format("%s hit for %s damage",currentAction.target.getName(), String.valueOf(damageDealt));
                    targetDead = currentAction.target.checkDeath();

                } else {
                    damageText = "The attack missed!";
                    targetDead = false;
                }

                eventScreen.addMessages(new String[] {damage.flavorText, damageText});
                if(targetDead){
                    eventScreen.addMessages(new String[] {String.format("%s has died", currentAction.target.getName())});
                }
                advanceState(CombatPhase.ACTION_COMPLETE);

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
            advanceState(CombatPhase.ACTIONSELECT_COMPLETE);
        }
    }

    public void checkWinConditions(){
        // TODO: Break up this logic
        // remove any actions from dead combatants
        actionQueue.removeIf(action -> !action.combatant.canAttack());
        // Check for total party wipe
        boolean isAnyoneAlive = false;
        for(PartyCharacter partyMember: this.game.gameState.party.values()){
            if(!partyMember.isDead){
                isAnyoneAlive = true;
            }
        }
        if(!isAnyoneAlive){
            eventScreen.addMessages(new String[] {"All adventurers have died!"});
            advanceState(CombatPhase.LOSS);
            return;
        }
        // add XP
        for(Enemy enemy: this.game.gameState.currentEnemyRoster.values()){
            if(enemy.isDead){
                this.xpGained += enemy.earnedXP;
            }
        }
        // Check for dead enemies and remove from board
        // TODO: Terrible. Do not remove from game state, move to combat state instead
        this.game.gameState.currentEnemyRoster.values().removeIf(enemy -> enemy.checkDeath());
        // check for total enemy wipe
        if(this.game.gameState.currentEnemyRoster.isEmpty()){
            eventScreen.addMessages(new String[] {"All enemies have been vanquished!"});

            eventScreen.addMessages(new String[] {
                String.format("You have gained %s experience points from the fight", String.valueOf(xpGained))
            });

            advanceState(CombatPhase.VICTORY);
            return;
        }
        //else 
        advanceState(CombatPhase.NEW_ROUND);
    }

    public void rewards(){
                // TODO: bad
        this.game.gameState.player.xp = this.game.gameState.player.xp + xpGained;

        Random roll = new Random();
        int addGold = roll.nextInt(20) + 1;
        this.game.gameState.gold = this.game.gameState.gold + addGold;
        eventScreen.addMessages(new String[] {
                String.format(
                    "You earn %s gold from this fight",
                    String.valueOf(addGold)
                )
            }
        );

        int nextLevel = this.game.gameState.player.level + 1;
        if(game.gameState.player.xp >= LevelTable.getRequiredXp(nextLevel)){
            ArrayList<String> messages = game.gameState.player.LevelUp(nextLevel);
            eventScreen.addMessages(messages.toArray(new String[0]));
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

    public void notifyOnLoss(){
        for(CombatLogicObserver listener: combatLogicObservers){
            listener.onLoss();
        }
    }

    public void notifyOnVictory(){
        for(CombatLogicObserver listener: combatLogicObservers){
            listener.onVictory();
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

    
}
