package dungeon.crawler.GameSystem.SaveGame.Serialization;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.Inventory.Item;

import java.util.ArrayList;

public class GameSerializer {

    public static GameSave serializeGameState(GameState gameState) {

        GameSave saveState = new GameSave();

        saveState.overWorldCoordinatesX = (int) gameState.overWorldCoordinates.x;
        saveState.overWorldCoordinatesY = (int) gameState.overWorldCoordinates.y;
        saveState.gold = gameState.gold;

        saveState.player = serializePartyCharacter(gameState.player);

        saveState.party = new ArrayList<>();

        for (PartyCharacter character : gameState.party.values()) {
            saveState.party.add(serializePartyCharacter(character));
        }

        return saveState;
    }


    private static PartyCharacterSave serializePartyCharacter(
        PartyCharacter character
    ) {

        PartyCharacterSave save = new PartyCharacterSave();

        // Basic character state
        save.level = character.level;
        save.xp = character.xp;

        save.strength = character.strength;
        save.agility = character.agility;
        save.intelligence = character.intelligence;
        save.perception = character.perception;

        save.isHero = character.isHero;
        save.toHit = character.toHit;

        // Character state inherited from Character
        save.maxHp = character.maxHp;
        save.maxMP = character.maxMP;
        save.hp = character.hp;
        save.mp = character.mp;

        save.stance = character.stance;
        save.conditions = new ArrayList<>(character.conditions);
        save.isDead = character.isDead;

        // Class is represented by its name
        save.charClass = character.charClass.getName();

        // Inventory is represented by item IDs
        save.inventory = new ArrayList<>();

//        for (Item item : character.inventory.inventoryList) {
//            save.inventory.add(item.getID());
//        }

        // Spells are represented by IDs
        save.spells = new ArrayList<>();

        // Equipment
        save.equipment = new java.util.HashMap<>();

        // TODO: copy equipped items into save.equipment

        return save;
    }
}
