package dungeon.crawler.GameSystem.Inventory.EquipmentSystem;

import dungeon.crawler.GameSystem.Inventory.Armor;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

public class EquipmentSystem {
    private Weapon rightHand;
    private Armor leftHand;
    private Armor head;
    private Armor body;
    private Armor feet;

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

    public void equipItem(Item item){
        if(item.returnItemType() == ItemType.WEAPON){
            Weapon mainWeapon = (Weapon) item;
            rightHand = mainWeapon;
            // equipItem
        } else if (item.returnItemType() == ItemType.ARMOR){
            // consider using visitor pattern if this gets more complicated
            // item.equip(this)
            // item knows which slot it belongs to and chooses method
            Armor equippableArmor = (Armor) item;
            switch(equippableArmor.slot){
                case LEFT_HAND:
                    leftHand = equippableArmor;
                case HEAD:
                    head = equippableArmor;
                case BODY:
                    body = equippableArmor;
                case FEET:
                    feet = equippableArmor;
                default:
                    throw new IllegalArgumentException("Unknown Equipment Slot");
            }
        } else {
            throw new IllegalArgumentException("Unknown Item Type");
        }

    }

    public Weapon getWeapon(){
        return rightHand;
    }


}
