package dungeon.crawler.Utils;

import dungeon.crawler.GameSystem.Inventory.Item;

public class ItemUtils {
    public static String getItemName(Item item){
        if(item == null){
            return "None";
        }
        return item.getName();
    }
}
