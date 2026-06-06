package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.EquipmentSlot;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

public class Armor extends Item{
    public int defenseBonus;
    public EquipmentSlot slot;
    public Armor(
        String name,
        PartyCharacter owner,
        int defenseBonus,
        EquipmentSlot slot,
        int value
    ) {
        super(
            name,
            owner,
            value,
            ItemType.ARMOR
        );
        this.defenseBonus = defenseBonus;
        this.slot = slot;
    }
}
