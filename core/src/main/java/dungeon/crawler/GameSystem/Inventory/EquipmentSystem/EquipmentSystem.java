package dungeon.crawler.GameSystem.Inventory.EquipmentSystem;

import dungeon.crawler.GameSystem.Inventory.Armor;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

public class EquipmentSystem {
    private Weapon rightHand;
    private Armor leftHand;

    public EquipmentSystem(){

    }

    public EquipmentSystem(
        Weapon rightHand,
        Armor leftHand,
        Armor head,
        Armor body,
        Armor feet
    ) {
        this.rightHand = rightHand;
        this.leftHand = leftHand;
        this.head = head;
        this.body = body;
        this.feet = feet;
    }
    private Armor head;
    private Armor body;
    private Armor feet;

    public void equipItem(Item item){
        if(item.returnItemType() == ItemType.WEAPON){
            Weapon mainWeapon = (Weapon) item;
            rightHand = mainWeapon;
            // equipItem
        } else if (item.returnItemType() == ItemType.ARMOR){
            // check class restrictions
            // case for limb
        } else {
            // throw error of some sort
        }

    }


}
