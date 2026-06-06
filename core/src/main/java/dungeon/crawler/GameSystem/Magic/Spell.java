package dungeon.crawler.GameSystem.Magic;
import dungeon.crawler.GameSystem.Combat.Elemental;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Condition;

public class Spell implements SpellLogic{
    public String name;
    public int mpCost;
    public SpellType type;


    public Spell(
        String name,
        int mpCost,
        SpellType type
    ) {
        this.name = name;
        this.mpCost = mpCost;
        this.type = type;
    }

    @Override
    public String cast(Combatant caster, Combatant target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String cast(Combatant caster, ArrayList<Combatant> target) {
        // TODO Auto-generated method stub
        return null;
    }
}
