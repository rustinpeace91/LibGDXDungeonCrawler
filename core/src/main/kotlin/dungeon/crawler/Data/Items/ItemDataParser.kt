package dungeon.crawler.Data.Items

import dungeon.crawler.Data.Enemies.EnemyParams
import dungeon.crawler.GameSystem.Character.Condition
import dungeon.crawler.GameSystem.Combat.Elemental
import dungeon.crawler.GameSystem.Inventory.ItemTypes.Handed
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ItemType
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes

/* using the same pattern as the enemy registry. This is a lot of boiler plate necessary to
avoid reflection which breaks the browser build. I originally wanted to use
 JSON files for items, but Java is too strict to make this work easily
 with the way I have the game set up
I know this violates DRY but for now
the items are not varied enough that it really matters
 */

data class WeaponParams(
    val name: String,
    val id: String,
    val toHit: Int,
    val damageLow: Int,
    val damageHigh: Int,
    val flavorTextVerb: String,
    val ranged: Boolean,
    val condition: Condition?,
    val elemental: Elemental?,
    val value: Int,
    val itemType: ItemType,
    val weaponType: WeaponTypes,
    val handed: Handed
)

data class PotionParams(
    val name: String,
    val id: String,
    val value: Int,
    val itemType: ItemType,
    val level: Int,
    val cureStatus: Condition
)

class WeaponRegistry {
    private val weaponData = mutableMapOf<String, WeaponParams>();

    fun registerWeapon(id: String, params: WeaponParams) {
        weaponData[id] = params
    }

    fun getEnemyById(id: String): WeaponParams? {
        return weaponData.get(id);
    }

}

class PotionRegistry {
    private val potionData = mutableMapOf<String, PotionParams>();

    fun registerpotion(id: String, params: PotionParams) {
        potionData[id] = params
    }

    fun getEnemyById(id: String): PotionParams? {
        return potionData.get(id);
    }

}

object DataInitializer {
    @JvmStatic
    fun initializeWeaponData(): WeaponRegistry {
        val registry = WeaponRegistry()
        registry.registerWeapon(
            "iron_sword",
            WeaponParams(
                "iron sword",
                "iron_sword",
                1,
                2,
                10,
                "swings",
                false,
                null,
                null,
                5,
                ItemType.WEAPON,
                WeaponTypes.SHORTSWORD,
                Handed.ONE_HANDED
            )
        )
        return registry;
    }

//    fun initializePotionData(): WeaponRegistry {
//        val registry = WeaponRegistry()
//        registry.registerWeapon(
//            "rat",
//            WeaponParams(
//                "iron sword",
//                "iron_sword",
//                1,
//                2,
//                10,
//                "swings",
//                false,
//                null,
//                null,
//                5,
//                ItemType.WEAPON,
//                WeaponTypes.SHORTSWORD,
//                Handed.ONE_HANDED
//            )
//        )
//        return registry;
//    }
}
