package dungeon.crawler.GameSystem.Inventory;


import com.badlogic.gdx.Gdx;
import dungeon.crawler.Data.Items.ShopItemConfig;
import dungeon.crawler.GameSystem.TestData.ItemFactory;

import java.util.*;


public class ShopItemSpawner {
    public static ArrayList<Item> spawnItems(int shopIndex) {
        ItemFactory factory = new ItemFactory();

        ArrayList<Item> inventory = new ArrayList<Item>();
        List<String> inventoryIDs = ShopItemConfig.registry.get(shopIndex);
        if(inventoryIDs == null){
            inventoryIDs = ShopItemConfig.registry.get(0);
            Gdx.app.log("[ERROR]", "Shop Index provided that has not been created yet");
        }
        for(String itemName: inventoryIDs){
            try{
                inventory.add(factory.createItemById(itemName));
            } catch (Exception e) {
                Gdx.app.log("[ERROR]", "error creating Shop inventory: " + e.getMessage());
            }
        }
        return inventory;
    }

//    private static ArrayList<String> difficultyCurve(int value){
//        switch(value){
//            default:
//                ArrayList<Item> inventory = new ArrayList<>();
//                inventory.add()
//
//        }
//    }
}


//public class EnemySpawner {
//    public static Map<Integer, EnemyCombatant> spawnEnemies(GameState gameState, TiledMapTileLayer.Cell tileCell) {
//        MapProperties props = tileCell.getTile().getProperties();
//        Map<Integer, EnemyCombatant> enemies = new HashMap<>();
//        Random diceRoller = new Random();
//        EnemyFactory factory = new EnemyFactory();
////        if(!props.containsKey("tile_difficulty")){
////
////        }
//        int numberOfEnemies = diceRoller.nextInt(3) + 1;
//        ArrayList<String> enemySelection = difficultyCurve(0);
//        for(int i = 0; i < numberOfEnemies; i++){
//            Collections.shuffle(enemySelection);
//            enemies.put(i, factory.createEnemyFromID(enemySelection.get(0)));
//        }
//        return enemies;
//    }
//
//    private static ArrayList<String> difficultyCurve(int value){
//        switch(value){
//            default:
//                return new ArrayList<String>(Arrays.asList("rat", "rat", "rat", "rat", "spider"));
////                return new ArrayList<String>(Arrays.asList("skeleton", "skeleton"));
//
//        }
//    }
//}
