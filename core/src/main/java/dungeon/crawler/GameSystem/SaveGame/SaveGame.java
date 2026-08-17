package dungeon.crawler.GameSystem.SaveGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import dungeon.crawler.GameSystem.GameState.GameState;

public class SaveGame {
    FileHandle saveFile = Gdx.files.local("savegame.json");

    public SaveGame(){
        saveFile = Gdx.files.local("savegame.json");
    }

    public String saveGameState(GameState gameState){
        Json json = new Json();
        String saveData = json.toJson(gameState);
        saveFile.writeString(saveData, false);
        return saveData;

    }

    public GameState loadGameState(){

        if (!saveFile.exists()) {
            return null;
        }
        Json json = new Json();


        String saveData = saveFile.readString();
        //special handling here
        GameState loadedGameState = json.fromJson(GameState.class, saveData);

        return loadedGameState;

    }
}
