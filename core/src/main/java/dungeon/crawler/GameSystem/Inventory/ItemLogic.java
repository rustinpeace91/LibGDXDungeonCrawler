import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

public interface ItemLogic {
    public ItemType returnItemType();
    public boolean equippable();
    public boolean canEquip(ClassLogic charClass);
}
