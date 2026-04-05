package dungeon.crawler.GameSystem.TestData;

import dungeon.crawler.GameSystem.Inventory.Weapon;

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
            null
        );
    }
}
