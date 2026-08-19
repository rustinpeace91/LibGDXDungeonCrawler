package dungeon.crawler.GameSystem.SaveGame.Serialization;

import dungeon.crawler.GameSystem.GameState.GameState;

public class GameSerializer {

    public static GameSave serializeGameState(GameState gameState){
        GameSave saveState = new GameSave();
        saveState.overWorldCoordinatesX = (int) gameState.overWorldCoordinates.x;
        saveState.overWorldCoordinatesY = (int) gameState.overWorldCoordinates.y;
        saveState.gold = gameState.gold;

    }
}
