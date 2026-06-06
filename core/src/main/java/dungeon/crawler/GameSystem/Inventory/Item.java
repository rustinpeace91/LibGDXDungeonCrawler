package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
// import dungeon.crawler.GameSystem.TestData.PlayerCharacter;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

public class Item implements ItemLogic {
    public String name;
    public PartyCharacter owner;
    public int value;
    public Item(
        String name,
        PartyCharacter owner,
        int value,
        ItemType itemType
    ) {
        this.name = name;
        this.owner = owner;
        // the original value of the item for sale, shopowners will  add their own tax to this value
        this.value = value;
    }
    @Override
    public ItemType returnItemType() {
        // TODO Auto-generated method stub
        return null;
    }
}
