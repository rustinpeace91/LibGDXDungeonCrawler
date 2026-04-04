package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
// import dungeon.crawler.GameSystem.TestData.PlayerCharacter;

public class Item {
    public String name;
    public PartyCharacter owner;
    public Item(
        String name,
        PartyCharacter owner
    ) {
        this.name = name;
        this.owner = owner;
    }
}
