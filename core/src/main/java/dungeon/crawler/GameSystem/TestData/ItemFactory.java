package dungeon.crawler.GameSystem.TestData;

import dungeon.crawler.Data.Items.*;
import dungeon.crawler.GameSystem.Inventory.Armor;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.Potion;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import org.jetbrains.annotations.NotNull;


public class ItemFactory {

    private final Registry<ArmorParams> armorRegistry;
    private Registry<WeaponParams> weaponRegistry;
    private Registry<PotionParams> potionRegistry;

    public ItemFactory(){
        this.weaponRegistry = ItemDataInitializer.initializeWeaponData();
        this.potionRegistry = ItemDataInitializer.initializePotionData();
        this.armorRegistry = ItemDataInitializer.initializeArmorData();
    }

    public Weapon createWeaponFromID(String id) {
        WeaponParams params = weaponRegistry.getById(id);
        return new Weapon(
            params.getName(),
            params.getId(),
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
            params.getId(),
            params.getValue(),
            params.getItemType(),
            params.getLevel(),
            params.getCureStatus()
        );
    }

    public Armor createArmorFromID(String id) {
        ArmorParams params = armorRegistry.getById(id);
        return new Armor(
            params.getName(),
            params.getId(),
            params.getValue(),
            params.getArmorType(),
            params.getSlot(),
            params.getDefenseBonus()
        );
    }

    public Item createItemById(String id) {
        if (weaponRegistry.getById(id) != null) {
            return createWeaponFromID(id);
        }
        if (potionRegistry.getById(id) != null) {
            return createPotionFromID(id);
        }
        if (armorRegistry.getById(id) != null) {
            return createArmorFromID(id);
        }
        throw new IllegalArgumentException("Unknown item ID: " + id);
    }

}
