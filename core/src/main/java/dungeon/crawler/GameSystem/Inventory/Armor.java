package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.PartyCharacter;

public class Armor extends Item{
    public int defenseBonus;
    public EquipmentSlot slot;
    public Armor(
        String name,
        PartyCharacter owner,
        int defenseBonus,
        EquipmentSlot slot
    ) {
        super(name, owner);
        this.defenseBonus = defenseBonus;
        this.slot = slot;
    }
    
}
