package dungeon.crawler.GameSystem.Character;

import dungeon.crawler.GameSystem.Inventory.Item;

public interface Inventory {
    public String addToInventory(Item item);
    public String removeFromInventory(Item item);
    public boolean enoughSpace();
    public String getName();

}
