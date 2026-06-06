package dungeon.crawler.GameSystem.Magic.Spells;
import java.util.Random;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Magic.*;
import dungeon.crawler.Utils.StringUtils;

public class CureMinor extends Spell {
    public CureMinor(){
        super(
            "Cure Minor Wounds",
            5,
            SpellType.SINGLE_DEFENSE
        );
    }

    @Override
    public String cast(Combatant caster, Combatant target) {
        if(target.checkDeath()){
            return StringUtils.format(
                "%s cannot cast heal because target is dead", caster.getName()
            );
        }

        if(!caster.canAttack()) {
            return StringUtils.format(
                "%s has been rendered unable to cast spell", caster.getName()
            );
        }

        Random roller = new Random();
        int roll = roller.nextInt(8) + 1;
        int boost = target.heal(roll);
        return StringUtils.format(
            "%s healed %s for %s points",
            caster.getName(),
            target.getName(),
            String.valueOf(boost)
        );
    }
}


