package dungeon.crawler.GameSystem.SaveGame.Serialization;

import java.util.ArrayList;

public class GameSave {
    public PartyCharacterSave player;
    public int overWorldCoordinatesX;
    public int overWorldCoordinatesY;
    public ArrayList<PartyCharacterSave> party;

    public int gold;
    public boolean isPlayerDead;
    public ArrayList<String> bag;
    public GameSave(){
    }
}
