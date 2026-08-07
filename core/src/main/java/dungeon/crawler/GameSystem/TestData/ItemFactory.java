package dungeon.crawler.GameSystem.TestData;

import dungeon.crawler.Data.Items.ItemDataInitializer;
import dungeon.crawler.Data.Items.PotionParams;
import dungeon.crawler.Data.Items.Registry;
import dungeon.crawler.Data.Items.WeaponParams;
import dungeon.crawler.GameSystem.Inventory.Potion;
import dungeon.crawler.GameSystem.Inventory.Weapon;


public class ItemFactory {

    private Registry<WeaponParams> weaponRegistry;
    private Registry<PotionParams> potionRegistry;

    public ItemFactory(){
        this.weaponRegistry = ItemDataInitializer.initializeWeaponData();
        this.potionRegistry = ItemDataInitializer.initializePotionData();
    }

    public Weapon createWeaponFromID(String id) {
        WeaponParams params = weaponRegistry.getById(id);
        return new Weapon(
            params.getName(),
            params.getId(),
            null,
            params.getToHit(),
            params.getDamageLow(),
            params.getDamageHigh(),
            params.getFlavorTextVerb(),
            params.getRanged(),
            params.getCondition(),
            params.getElemental(),
            params.getValue(),
            params.getWeaponType(),
            params.getHanded()
        );
    }

    public Potion createPotionFromID(String id){
        PotionParams params = potionRegistry.getById(id);
        return new Potion(
            params.getName(),
            null,
            params.getValue(),
            params.getItemType(),
            params.getLevel(),
            params.getCureStatus()
        );
    }

}
