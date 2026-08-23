package dungeon.crawler.GameSystem.GameState;

import java.lang.reflect.Array;
import java.util.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

import dungeon.crawler.GameSystem.Character.Bag;
import dungeon.crawler.GameSystem.Character.EnemyCombatant;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameBuild;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.SaveGame.Serialization.GameSave;
import dungeon.crawler.GameSystem.SaveGame.Serialization.PartyCharacterSave;
import dungeon.crawler.GameSystem.TestData.PlayerFactory;

public class GameState {
    public PartyCharacter player;
    public Vector2 overWorldCoordinates;

    public transient Map<Integer, PartyCharacter> party;

    public int gold;
    public boolean isPlayerDead;
    public String currentMap;
    public int screenID;
    public Bag partyBag;
    // used for enemy encounters, shop stuff?
    private transient int tileDifficulty = 0;
    private GameBuild build;

    public GameState(GameBuild build){
        this.build = build;
    }

    public GameState(){

    }


    public void setupGameFromPresetParty(){
        String[] partyStrings = {"Hero", "Fighter", "Wizard", "Thief"};
        setUpPartyFromClassString(new ArrayList<>(Arrays.asList(partyStrings)));
        setUpDefaultValues();
    }

    public void setupGameFromCustomParty(ArrayList<String> partyList){
        setUpPartyFromClassString(partyList);
        setUpDefaultValues();
    }


    public void setUpPartyFromClassString(ArrayList<String> partyList){
        party = new HashMap<>();
        player = PlayerFactory.generate();
        for(int i = 0; i < partyList.size(); i++){
            String className = partyList.get(i);
            PartyCharacter chr = PlayerFactory.generateClass(className);
            party.put(i, chr);
        }

    }

    public void setUpDefaultValues(){
        partyBag = new Bag();
        overWorldCoordinates = new Vector2(0,0);
        gold = 100;
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

    public void setTileDifficulty(int tileDifficulty) {
        this.tileDifficulty = tileDifficulty;
    }
    public int getTileDifficulty() {
        return tileDifficulty;
    }
    public int getGold(){
        return gold;
    }



    public GameBuild getBuild() {
        return build;
    }

    public void setBuild(GameBuild build) {
        this.build = build;
    }

    public void populateGameState(GameSave gameSave){
        gold = gameSave.getGold();
        isPlayerDead = false;
        currentMap = gameSave.getCurrentMap();
        screenID = gameSave.getScreenID();
        // stupid browsers do not like floats or vectors
        Vector2 coords = new Vector2();
        coords.x = (float) gameSave.getOverWorldCoordinatesX();
        coords.y = (float) gameSave.getOverWorldCoordinatesY();
        overWorldCoordinates = coords;
        List<PartyCharacterSave> partySave = gameSave.getParty();
        party = new HashMap<>();
        for(int i = 0; i < gameSave.getParty().size(); i++){
            PartyCharacter newChar = PlayerFactory.generateFromSaveState(
                partySave.get(i)
            );
            party.put(i, newChar);
        }
        /* TODO: get rid of 'player', it's stupid and we don't need it anymore */
        player = party.get(0);
        Bag bag = new Bag();
        for(Item i: PlayerFactory.returnItemsFromSave(gameSave.getBag())){
            bag.addToInventory(i);
        }
        partyBag = bag;
    }

}
