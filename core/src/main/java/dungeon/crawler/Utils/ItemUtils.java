package dungeon.crawler.Utils;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Inventory.Item;

public class ItemUtils {
    public static String getItemName(Item item){
        if(item == null){
            return "None";
        }
        return item.getName();
    }

    public static boolean canTransferItem(PartyCharacter sender, PartyCharacter reciever, Item item){
        return reciever.inventory.enoughSpace();
    }

    public static void transferItem(PartyCharacter sender, PartyCharacter reciever, Item item){
        sender.removeFromInventory(item);
        reciever.addToInventory(item);
    }
}
