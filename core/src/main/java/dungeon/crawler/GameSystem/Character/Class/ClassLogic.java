package dungeon.crawler.GameSystem.Character.Class;

import java.util.ArrayList;
import java.util.Map;

import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.ArmorTypes;
import dungeon.crawler.GameSystem.Inventory.ItemTypes.WeaponTypes;
import dungeon.crawler.GameSystem.Magic.MagicSystem;

public interface ClassLogic{
    public Map<GameConstants.PLAYER_STATS, Integer> returnBaseStats();
    public Map<GameConstants.PLAYER_STATS, Integer> returnLevelUpStats();
    public String getName();
    public void fillSpells(int level);
    public MagicSystem getMagicSystem();
    public boolean isMagicUser();
    public int getBaseHP();
    public int getBaseMP();
    public int getLevelUpHP();
    public int getLevelUpMP();
    public ArrayList<WeaponTypes> getWeaponRestrictions();
    public ArrayList<ArmorTypes> getArmorRestrictions();
}
