package dungeon.crawler.GameSystem.TestData;

import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.Handed;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;

public class WeaponFactory {

    public static Weapon getIronSword() {
        return new Weapon(
            "Iron sword",
            null,
            1,
            2,
            10,
            "swings",
            false,
            null,
            null,
            5,
            WeaponTypes.SHORTSWORD,
            Handed.ONE_HANDED
        );
    }
}
