package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;
import dungeon.crawler.GameSystem.Character.Class.HeroClass;

public class PlayerFactory{

	public static PartyCharacter generate() {
    	
    	HeroClass hc = new HeroClass();
    	// TODO: Get base stats from charClass method
        PartyCharacter pc = new PartyCharacter(
            "Hero",
            30,
            10,
            30,
            10,
            0,
            10,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            10,
            10,
            10,
            10,
            hc,
            true
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }
	
	// TODO: make fighter class
	HeroClass fc = new HeroClass();
	// TODO: Get base stats from charClass method
    public static PartyCharacter generatePartyMember() {
        PartyCharacter pc = new PartyCharacter(
            "Foighter",
            45,
            0,
            45,
            10,
            0,
            10,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            12,
            10,
            8,
            6,
            fc,
            false
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }


}