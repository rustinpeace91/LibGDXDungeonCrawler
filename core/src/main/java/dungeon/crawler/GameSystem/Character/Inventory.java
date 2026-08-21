package dungeon.crawler.GameSystem.Character;

import dungeon.crawler.GameSystem.Inventory.Item;

import java.util.ArrayList;

public interface Inventory {
    public String addToInventory(Item item);
    public String removeFromInventory(Item item);
    public boolean enoughSpace();
    public String getName();
    public ArrayList<Item> returnInventory();

}
