package dungeon.crawler.GameSystem.Combat;

import dungeon.crawler.Data.Spells.Spell;
import dungeon.crawler.Data.Spells.SpellRegistry;
import dungeon.crawler.Data.Spells.SpellType;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;
import dungeon.crawler.GameSystem.Inventory.InventorySystem.InventorySystem;
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
    public ArrayList<String> handleAttack(CombatAction currentAction){
        String damageText = "";
        ArrayList<String> flavorText = new ArrayList<>();
        boolean targetDead = false;
        if(currentAction.target.checkDeath()){

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

    public ArrayList<String> handleMiscAction(CombatAction currentAction){
        String damageText = "";
        ArrayList<String> flavorText = new ArrayList<>();
        boolean targetDead = false;
        if(!currentAction.target.checkDeath()){
            switch(currentAction.action){
                case STAND:
                    currentAction.combatant.setStance(Stance.STANDING);
                    flavorText.add(StringUtils.format("%s has stood up", currentAction.combatant.getName()));
                    break;
                default:
                    flavorText.add("nothin happens");
                    break;


            }

        } else {
            flavorText.add(StringUtils.format("%s is dead and cannot act", currentAction.combatant.getName()));
        }
        return flavorText;
    }

    public ArrayList<String> handleSpell(CombatAction currentAction){
        Spell spell = SpellRegistry.INSTANCE.get(currentAction.spell);
        if(spell.getType() == SpellType.AOE_DEFENSE || spell.getType() == SpellType.AOE_OFFENSE){
            return handleMultiSpell(currentAction);
        } else {
            return handleSingleSpell(currentAction);
        }
    }

    public ArrayList<String> handleItemUse(CombatAction currentAction){
        ArrayList<String> flavorText = new ArrayList<>();
        if(currentAction.target.checkDeath()){
            flavorText.add(StringUtils.format("%s cannot use item as target is dead", currentAction.combatant.getName()));
            return flavorText;
        } else {
            flavorText = currentAction.item.use(currentAction.target);
            if(currentAction.combatant instanceof PartyCharacter) {
                PartyCharacter character = (PartyCharacter)currentAction.combatant;
                character.removeFromInventory(currentAction.item);
            }
        }
        return flavorText;
    }

    public ArrayList<String> handleSingleSpell(CombatAction currentAction){
        ArrayList<String> flavorText = new ArrayList<>();
        boolean targetDead = false;
        Spell spell = SpellRegistry.INSTANCE.get(currentAction.spell);

        if(spell.getType() == SpellType.SINGLE_OFFENSE){
            currentAction.target = nextAvailableEnemy(currentAction);
            if(currentAction.target == null){
                flavorText.add(StringUtils.format("%s cannot cast as all enemies have been defeated", currentAction.combatant.getName()));
                return flavorText;
            }
        }
        if(spell.getType() == SpellType.SINGLE_DEFENSE){
            if(currentAction.target != null && currentAction.target.checkDeath()){
                flavorText.add(StringUtils.format("%s is dead and cannot be healed", currentAction.target.getName()));
                return flavorText;
            }
        }

        if(spell.getType() == SpellType.RESURRECTION){
            if(currentAction.target != null && !currentAction.target.checkDeath()){
                flavorText.add(StringUtils.format("%s is alive and cannot be resurrected", currentAction.target.getName()));
                return flavorText;
            }
        }

        if(currentAction.target != null) {
            currentAction.combatant.spendMp(spell.getCost());
            ArrayList<Combatant> targets = new ArrayList();
            targets.add(currentAction.target);
            flavorText = spell.cast(
                currentAction.combatant, targets
            );
            if(currentAction.target != null && currentAction.target.checkDeath()){
                flavorText.add(StringUtils.format("%s has died", currentAction.target.getName()));
            }
        }
        return flavorText;
    }

    public ArrayList<String> handleMultiSpell(CombatAction currentAction){
        ArrayList<String> flavorText = new ArrayList<>();
        boolean targetDead = false;
        Spell spell = SpellRegistry.INSTANCE.get(currentAction.spell);
        ArrayList<Combatant> targets;
        if(spell.getType() == SpellType.AOE_OFFENSE){
            targets = new ArrayList(CombatUtils.returnAliveCombatants(enemyRoster).values());
        } else {
            targets = new ArrayList(CombatUtils.returnAliveCombatants(playerRoster).values());
        }
        flavorText = spell.cast(
            currentAction.combatant, targets
        );

        return flavorText;
    }


    public Combatant nextAvailableEnemy(CombatAction currentAction){
        Combatant target = currentAction.target;
        if(target.checkDeath()){
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
