package dungeon.crawler.GameSystem.SaveGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.SaveGame.Serialization.GameSave;
import dungeon.crawler.GameSystem.SaveGame.Serialization.SaveJsonParser;
import dungeon.crawler.GameSystem.SaveGame.Serialization.GameSerializer;

public class SaveGame {
    FileHandle saveFile = Gdx.files.local("savegame.json");

    public SaveGame(){
        saveFile = Gdx.files.local("savegame.json");
    }

    public String saveGameState(GameState gameState){
        /* write savegame to filesystem and return save string (for logging)*/
        GameSave save = GameSerializer.serializeGameState(gameState);
        String saveData = SaveJsonParser.encode(save);
        Gdx.app.log("[BROWSER SAVE]", saveData);
        saveFile.writeString(saveData, false);
        return saveData;

    }

    public GameState loadGameState() {
        /*load game from file system and return a GameState, or null if error*/
        if (!saveFile.exists()) {
            return null;
        }

        try {
            String saveData = saveFile.readString();

            GameSave decodedData = SaveJsonParser.decode(saveData);
            GameState loadedGameState = new GameState();
            loadedGameState.populateGameState(decodedData);
            return loadedGameState;
        } catch (Exception e) {
            Gdx.app.log("[LOAD GAME]", e.getMessage());
            return null;
        }
    }
}
