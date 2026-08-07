package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

import java.util.ArrayList;

public interface ItemLogic {
    public ItemType returnItemType();
    public boolean equippable();
    public boolean canEquip(ClassLogic charClass);
    public ArrayList<String> use(Combatant target);
}
