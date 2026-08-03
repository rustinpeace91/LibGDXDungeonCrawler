package dungeon.crawler.GameSystem.Inventory;

import dungeon.crawler.GameSystem.Character.Combatant;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType;

import java.util.ArrayList;

public class Potion extends Item{
    public int level;
    public Condition cureStatus;

    public Potion(String name, PartyCharacter owner, int value, ItemType itemType, int level, Condition cureStatus) {
        super(name, owner, value, itemType);
        this.level = level;
        this.cureStatus = cureStatus;
    }

    @Override
    public ArrayList<String> use() {
        return null;
    }

    @Override
    public ArrayList<String> use(Combatant target) {
        return null;
    }

}
