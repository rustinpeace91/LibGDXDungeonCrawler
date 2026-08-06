package dungeon.crawler.GameSystem.Character;

import dungeon.crawler.GameSystem.Combat.AttackDamage;

public interface Combatant {
    AttackDamage  attack();
    int defend(AttackDamage attack);
    int takeHit(AttackDamage attack);
    void spendMp(int amount);
    int rollInitiative();
    int heal(int amount);
    // void boostDefense();
    boolean checkDeath();
    boolean canAttack();
    String getName();
    int getDefense();
    boolean playerAligned();
    void resurrect();
    void longRest();
    Stance getStance();
    boolean hasCondition(Condition condition);
    void addCondition(Condition condition);
    void removeCondition(Condition condition);
}
