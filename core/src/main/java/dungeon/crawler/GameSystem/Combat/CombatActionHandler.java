package dungeon.crawler.GameSystem.Combat;

import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellRegistry;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.Utils.StringUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;

public class CombatActionHandler {
    private Map<Integer, ? extends Combatant> playerRoster;
    private Map<Integer, ? extends Combatant> enemyRoster;

    public CombatActionHandler(
        Map<Integer, ? extends Combatant> playerRoster,
        Map<Integer, ? extends Combatant> enemyRoster
    ){
        this.playerRoster = playerRoster;
        this.enemyRoster = enemyRoster;
    }
    // all methods will take in a CombatAction,
    // handle it and return messages
    // should all switching logic be moved here?
    // maybe have a Notifier to add messages to event screen
    // how to handle inventory stuff?
    public ArrayList<String> handleAttack(CombatAction currentAction){
        String damageText = "";
        ArrayList<String> flavorText = new ArrayList<>();
        // fuck this
        boolean targetDead = false;
        if(currentAction.target.checkDeath()){
            // TODO: implement target switching logic;
            // implement Combatant interface that returns player or enemy side
            // if statement here
            Map.Entry<Integer, Combatant> availableCombatant;
            if (currentAction.combatant.playerAligned()) {
                availableCombatant = CombatUtils.returnAliveCombatants(
                    enemyRoster
                ).entrySet().stream().findAny().orElse(null);
            } else {
                availableCombatant = CombatUtils.returnAliveCombatants(
                    playerRoster
                ).entrySet().stream().findAny().orElse(null);
            }
            if(availableCombatant.getValue() != null){
                currentAction.target = availableCombatant.getValue();
                handleAttack(currentAction);
            } else {
                flavorText.add(StringUtils.format("%s swings at nothing as all enemies are dead", currentAction.combatant.getName()));
            }
        } else {
            AttackDamage damage = currentAction.combatant.attack();
            int defense = currentAction.target.defend(damage);

            if(damage.toHit > defense){
                int damageDealt = currentAction.target.takeHit(damage);
                damageText = StringUtils.format("%s hit for %s damage",currentAction.target.getName(), String.valueOf(damageDealt));
                targetDead = currentAction.target.checkDeath();

            } else {
                damageText = "The attack missed!";
                targetDead = false;
            }

            flavorText.add(damage.flavorText);
            flavorText.add(damageText);
            if(targetDead){
                flavorText.add(StringUtils.format("%s has died", currentAction.target.getName()));
            }
        }
        return flavorText;
    }

    public ArrayList<String> handleOffensiveSpell(CombatAction currentAction){
        String damageText = "";
        ArrayList<String> flavorText = new ArrayList<>();
        // fuck this
        boolean targetDead = false;
        currentAction.target = nextAvailableEnemy(currentAction);

        if(currentAction.target != null) {
            AttackDamage damage = currentAction.combatant.attack();
            Spell spell = SpellRegistry.INSTANCE.get(currentAction.spell);
            currentAction.combatant.spendMp(spell.getCost());
            ArrayList<Combatant> targets = new ArrayList();
            targets.add(currentAction.target);
            flavorText = spell.cast(
                currentAction.combatant, targets
            );
            flavorText.add(damage.flavorText);
            flavorText.add(damageText);
            if(currentAction.target != null && currentAction.target.checkDeath()){
                flavorText.add(StringUtils.format("%s has died", currentAction.target.getName()));
            }
        } else {
            flavorText.add(StringUtils.format("%s cannot cast as all enemies have been defeated", currentAction.combatant.getName()));
        }
        return flavorText;
    }

    public Combatant nextAvailableEnemy(CombatAction currentAction){
        Combatant target = currentAction.target;
        if(target.checkDeath()){
            // TODO: implement target switching logic;
            // implement Combatant interface that returns player or enemy side
            // if statement here
            Map.Entry<Integer, Combatant> availableCombatant;
            if (currentAction.combatant.playerAligned()) {
                availableCombatant = CombatUtils.returnAliveCombatants(
                    enemyRoster
                ).entrySet().stream().findAny().orElse(null);
            } else {
                availableCombatant = CombatUtils.returnAliveCombatants(
                    playerRoster
                ).entrySet().stream().findAny().orElse(null);
            }
            if(availableCombatant.getValue() != null){
                return availableCombatant.getValue();
            } else {
                return null;
            }
        } else {
            return target;
        }
    }
}
