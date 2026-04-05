// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.utils.Json;
// import com.badlogic.gdx.utils.ObjectMap;

// import dungeon.crawler.GameSystem.Inventory.Weapon;

// public class WeaponFactory {
//     private static ObjectMap<String, WeaponData> library;

//     public static void init() {
//         Json json = new Json();
//         // This reads the whole file into a map of blueprints
//         library = json.fromJson(ObjectMap.class, WeaponData.class, Gdx.files.internal("weapons.json"));
//     }

//     public static Weapon create(String key, PartyCharacter owner) {
//         WeaponData data = library.get(key);
//         if (data == null) return null;

//         return new Weapon(
//             data.name, owner, data.toHit, data.damageLow, data.damageHigh,
//             data.flavorTextVerb, data.ranged, data.condition, data.elemental,
//             data.classRestrictions
//         );
//     }
// }












































