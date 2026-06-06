package dungeon.crawler.GameSystem.Magic;
// import dungeon.crawler.targetameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.Combatant;
import java.util.ArrayList;

public interface SpellLogic {
    public String cast(Combatant caster, Combatant target);
    public String cast(Combatant caster, ArrayList<Combatant> target);
}
