package dungeon.crawler.Utils;

import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Inventory.Armor;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;
import dungeon.crawler.GameSystem.Inventory.Weapon;

import java.util.Arrays;

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
        if(sender.equipment.isEquipped(
           item
        )){
            sender.unEquip(item);
        }
        reciever.addToInventory(item);
    }

    public static void useItem(PartyCharacter user, Item item){
        item.use(user);
        user.removeFromInventory(item);
        // TODO: handle strings and other stuff here
    }

    public static void equipItem(PartyCharacter user, Item item){
        item.use(user);
        user.equip(item);
    }

    public static void unEquipItem(PartyCharacter user, Item item){
        item.use(user);
        user.unEquip(item);
    }


    public static String itemStats(PartyCharacter user, Item item){
        ItemType type = item.returnItemType();
        String statString = "";
        if(Arrays.asList(GameConstants.EQUIPPABLE_ITEMS).contains(type)){
            if(type == ItemType.WEAPON){
                Weapon newWeapon = (Weapon)item;
                Weapon currentWeapon = user.getWeapon();
                statString = "Damage: " + newWeapon.getAttackDamageString() + "\n" +
                    "Current: " + currentWeapon.getAttackDamageString();


            } else if(type == ItemType.ARMOR){
                Armor newArmor = (Armor)item;

                Item equippedItem = user.equipment.getItemBySlot(newArmor.slot);
                if(equippedItem.returnItemType() == ItemType.ARMOR){
                    Armor currentArmor = (Armor)equippedItem;
                    statString = "Protection: " + String.valueOf(newArmor.defenseBonus) + "\n" +
                        "Current: " + String.valueOf(currentArmor.defenseBonus);
                }
            }
        }
        return statString;
    }
}
