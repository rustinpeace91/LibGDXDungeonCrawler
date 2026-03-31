package dungeon.crawler.GameSystem.Character;

import dungeon.crawler.GameSystem.Combat.AttackDamage;

public interface Combatant {
    AttackDamage  attack();
    int defend(AttackDamage attack);
    int takeHit(AttackDamage attack);
    int rollInitiative();
    // void boostDefense();
    boolean checkDeath();
    String getName();
}
