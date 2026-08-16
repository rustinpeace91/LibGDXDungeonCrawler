package dungeon.crawler.GameSystem.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import dungeon.crawler.GameSystem.Character.Bag;
import dungeon.crawler.GameSystem.Character.EnemyCombatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
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
    public Bag partyBag;

    public GameState(){
        // TODO: make an actual constructor
    }
    // TODO: Refactor this shit so we're not repeating
    public void setUpTestData(){
        player = PlayerFactory.generate();
        PartyCharacter fighter = PlayerFactory.generatePartyMember();
        PartyCharacter wizard = PlayerFactory.generateWizard();
        PartyCharacter thief = PlayerFactory.generateThief();
        party = new HashMap<>();
        party.put(0, player);
        party.put(1, fighter);
        party.put(2, wizard);
        party.put(3, thief);
        partyBag = new Bag();
        overWorldCoordinates = new Vector2(0,0);
        currentEnemyRoster = new HashMap<>();
        gold = 600;
        isPlayerDead = false;
        currentMap = "";
        screenID = 1;
    }

    public void SetUpClassDataFromString(ArrayList<String> partyList){
        player = PlayerFactory.generate();
        party = new HashMap<>();
        for(int i = 0; i < partyList.size(); i++){
            String className = partyList.get(i);
            PartyCharacter chr = PlayerFactory.generateClass(className);
            party.put(i, chr);
        }
        partyBag = new Bag();
        overWorldCoordinates = new Vector2(0,0);
        currentEnemyRoster = new HashMap<>();
        gold = 600;
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

    public void addGold(int value){
        this.gold = gold + value;
    }

    public void removeGold(int value){
        this.gold = gold - value;
    }

    public int getGold(){
        return gold;
    }
}
