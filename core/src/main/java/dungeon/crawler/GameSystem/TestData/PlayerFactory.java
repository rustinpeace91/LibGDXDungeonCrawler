package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;

import static dungeon.crawler.GameConstants.PLAYER_STATS.*;

import dungeon.crawler.GameSystem.Character.Class.ClassLogic;
import dungeon.crawler.GameSystem.Character.Class.FighterClass;
import dungeon.crawler.GameSystem.Character.Class.HeroClass;

public class PlayerFactory{

    public static ClassLogic classSelector(String selector){
        // TODO: for when we implement savegames
        switch(selector){
            case "Hero":
                ClassLogic hc = new HeroClass();
                return hc;
            case "Fighter":
                ClassLogic fc = new FighterClass();
                return fc;
            default:
                throw new IllegalArgumentException("Unknown class type: " + selector);

        }
    }

	public static PartyCharacter generate() {

    	HeroClass hc = new HeroClass();
    	// TODO: Get base stats from charClass method
        //
        Map<GameConstants.PLAYER_STATS, Integer> statMap = hc.returnBaseStats();

        PartyCharacter pc = new PartyCharacter(
            "Hero",
            hc.getBaseHP(),
            hc.getBaseMP(),
            hc.getBaseHP(),
            hc.getBaseHP(),
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            hc,
            true
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }


    public static PartyCharacter generatePartyMember() {

        FighterClass fc = new FighterClass();
        Map<GameConstants.PLAYER_STATS, Integer> statMap = fc.returnBaseStats();
        PartyCharacter pc = new PartyCharacter(
            "Fightman",
            45,
            0,
            45,
            10,
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            fc,
            false
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }


}
