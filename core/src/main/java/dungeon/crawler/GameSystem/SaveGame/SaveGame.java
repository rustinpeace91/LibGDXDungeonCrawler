package dungeon.crawler.GameSystem.SaveGame;
import com.badlogic.gdx.utils.Json;
import dungeon.crawler.GameSystem.GameState.GameState;

public class SaveGame {
    public SaveGame(){}

    public String saveGameState(GameState gameState){
        Json json = new Json();
        return json.toJson(gameState);
    }

    public GameState loadGameState(String s){
        Json json = new Json();
        GameState loadedGameState = json.fromJson(GameState.class, s);
        return loadedGameState;
    }
}
