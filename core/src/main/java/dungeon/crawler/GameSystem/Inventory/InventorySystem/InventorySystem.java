package dungeon.crawler.GameSystem.Inventory.InventorySystem;

import dungeon.crawler.GameConstants;

import java.util.ArrayList;
import dungeon.crawler.GameSystem.Inventory.Item;

public class InventorySystem{
    //
    public ArrayList<Item> inventoryList;

    public InventorySystem(ArrayList<Item> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public boolean enoughSpace(){
        if(inventoryList.size() >= GameConstants.MAX_PLAYER_INVENTORY_SPACE) {
            return false;
        }
        return true;
    }

    public void addToInventory(Item item){
        inventoryList.add(item);
    }



}
