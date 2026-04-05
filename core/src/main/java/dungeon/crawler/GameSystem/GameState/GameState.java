package dungeon.crawler.GameSystem.GameState;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.TestData.EnemyCombatant;
import dungeon.crawler.GameSystem.TestData.PlayerFactory;

public class GameState {
    public PartyCharacter player;
    public Map<Integer, PartyCharacter> party;
    public Vector2 overWorldCoordinates;
    public Map<Integer, EnemyCombatant> currentEnemyRoster;
    public int gold;
    public boolean isPlayerDead;
    public String currentMap;
    public int screenID;

    public GameState(){
        // TODO: make an actual constructor
        setUpTestData();
    }

    public void setUpTestData(){
        player = PlayerFactory.generate();
        party = new HashMap<>();
        party.put(1, player);
        overWorldCoordinates = new Vector2(0,0);
        currentEnemyRoster = new HashMap<>();
        gold = 20;
        isPlayerDead = false;
        currentMap = "";
        screenID = 1;
    }

    public void updateWorldCoordinates(Vector2 newCoords){
        overWorldCoordinates = newCoords;
    }

    public void updateWorldMap(String mapFile){
        currentMap = mapFile;
    }
    public void updateScreenID(int id){
        screenID = id;
    }
}
