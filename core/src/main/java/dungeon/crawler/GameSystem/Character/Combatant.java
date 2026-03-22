package dungeon.crawler.GameSystem.Character;

import dungeon.crawler.GameSystem.Combat.AttackDamage;

public interface Combatant {
    AttackDamage  attack();
    void defend(AttackDamage attack);
    int takeHit(AttackDamage attack);
}
